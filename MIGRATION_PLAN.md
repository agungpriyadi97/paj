# 🛣️ MIGRATION PLAN: Katalon Studio → Playwright
## Proyek: "Pasti Ada Jalan" | Tanggal Audit: 9 Juli 2026

---

## 1. 📊 RINGKASAN EKSEKUTIF

| Item | Detail |
|---|---|
| **Aplikasi Target** | `https://d-speedshop-pastiadajalan.gtechdigital.id/` |
| **Framework Asal** | Katalon Studio 11.1.3 (Groovy/Selenium) |
| **Framework Tujuan** | Playwright (TypeScript) |
| **Total Modul Bisnis** | 11 Modul |
| **Total Test Cases** | ~120 test case |
| **CI/CD** | Jenkins (Jenkinsfile + GitLab CI) |
| **Data-Driven** | Excel (.xlsx) + .dat — 2 file ditemukan |
| **Custom Keywords** | 11 files (2 package: myorder/, utils/) |

---

## 2. 🗺️ PEMETAAN ALUR BISNIS UTAMA

Berdasarkan hasil scan folder `Test Cases/WEB/`, ditemukan **11 modul bisnis** dengan hirarki sebagai berikut:

```
WEB/
├── 🔐 Authentication/
│   ├── Login (Positive x1, Negative x8, DataDriven x1)
│   ├── Registration (Positive x1, Negative x11)
│   └── Forgot Password
│
├── 🏠 Home/
│   └── Positive (x8 — header, search, menus, guest access)
│
├── 🛍️ Product/
│   ├── PDP — Product Detail Page
│   ├── PLP — Product List Page
│   └── Category
│
├── 🛒 Cart/
│   └── Positive (x4 — add, remove, update qty, cart icon counter)
│
├── 💳 Checkout/
│   ├── Positive (x17 — payment, shipping, discount, Midtrans, VA BCA)
│   └── Positive - Cancel Order
│
├── 📍 Address Management/
│   ├── Address Checkout (add/edit/delete shipping & billing)
│   └── Address My Account (add/edit/delete)
│
├── 👤 Guest/
│   ├── Positive (x3 — explore, checkout, guest checkout flow)
│   └── Negative (x2 — invalid email, mandatory empty)
│
├── 📦 TrackOrder/
│   └── (x9 — tracking page, verification code, order detail)
│
├── ✉️ Email Verification/
│   └── Positive (x2 — registration email, forgot password email)
│
├── 🔗 External Integration/
│   └── WhatsApp (x8 — floating button, redirect, mobile view)
│
└── 📋 Content Verification/
    ├── Homepage, PLP, Checkout, Address
    ├── My Order, My Coupon, Account Setting
    ├── Store Location, Track Order, Registration
    └── (verifikasi copywriting UI)
```

### Alur Bisnis Kritis (Happy Path)

```
[Registrasi] → [Verifikasi Email via Mailinator] → [Login]
    → [Browse Produk/PLP] → [Buka PDP] → [Add to Cart]
        → [Checkout] → [Pilih Metode Pengiriman] → [Pilih Midtrans/VA BCA]
            → [Complete Payment] → [Verify Order Status]
                → [My Order] → [Track Order / Cancel Order]
```

---

## 3. 🐛 TEMUAN CODE SMELL & POTENSI FLAKY TEST

### 🔴 KRITIS — Penyebab Flaky Test Utama

#### CS-01: Hardcoded Sleep (`WebUI.delay`) — Tersebar MASIF
> **Risiko: SANGAT TINGGI**

Ditemukan **250+ baris** `WebUI.delay()` tersebar di seluruh proyek — baik di `Scripts/` maupun `Keywords/`.
Ini adalah **penyebab utama flaky test** karena:
- Delay terlalu pendek di environment CI (jaringan lambat) → test gagal
- Delay terlalu panjang di environment cepat → pemborosan waktu

**Contoh temuan:**
```groovy
// Scripts/WEB/Checkout/.../Script1781176108051.groovy
WebUI.delay(5)   // setelah login — menunggu apa?
WebUI.delay(10)  // setelah klik Add to Cart — hardcoded!
WebUI.delay(3)   // before checkout — tidak deterministic
WebUI.delay(10)  // setelah klik checkout — SANGAT LAMA

// Keywords/utils/BillingAddressHelper.groovy
WebUI.delay(6)   // delay 6 detik di helper → semua test yang pakai helper ini kena
```

**Solusi Playwright:**
```typescript
// Ganti dengan smart wait
await page.waitForSelector('[data-testid="order-success"]', { state: 'visible' })
await page.waitForLoadState('networkidle')
await expect(page.locator('.cart-badge')).toHaveText('1')
```

---

#### CS-02: Inter-Test Case Dependency via `WebUI.callTestCase` — 30+ Test Terdampak
> **Risiko: TINGGI**

Hampir **semua test case di 8 modul** bergantung pada satu test case tunggal:

```groovy
// Ditemukan di 30+ script
WebUI.callTestCase(
  findTestCase('WEB/Authentication/Login/Positive/Positive - Ensure user can login'),
  [:], FailureHandling.STOP_ON_FAILURE
)
```

Dan ada **double-chain dependency**:
```groovy
// Checkout: "customer receives order confirmation" memanggil:
WebUI.callTestCase(findTestCase('WEB/Checkout/Positive/Ensure user can checkout order successfully'), ...)
// Yang itu sendiri sudah memanggil login TC!
```

**Dampak:** Jika satu test login gagal → **seluruh suite runtuh** (cascade failure).

**Solusi Playwright:**
```typescript
// Gunakan shared setup / fixtures
// playwright.config.ts
export default defineConfig({
  use: { storageState: 'auth.json' }  // simpan sesi login sekali, reuse di semua test
})

// global-setup.ts
async function globalSetup() {
  const browser = await chromium.launch()
  const page = await browser.newPage()
  await page.goto('/sign-in')
  await page.fill('[placeholder="Email"]', process.env.LOGIN_EMAIL)
  await page.fill('[placeholder="Password"]', process.env.LOGIN_PASSWORD)
  await page.click('button.login_button')
  await page.context().storageState({ path: 'auth.json' })
}
```

---

#### CS-03: URL Produk Hardcoded di Script
> **Risiko: SEDANG-TINGGI**

```groovy
// Scripts/WEB/Checkout/.../Script1781176108051.groovy (line 32)
WebUI.navigateToUrl('https://d-speedshop-pastiadajalan.gtechdigital.id/pdp/SP260706006287')
```

URL produk hardcoded akan rusak jika SKU berubah, stok habis, atau environment berubah ke staging/production.

**Solusi Playwright:**
```typescript
// Navigasi dinamis melalui UI atau gunakan env var
const PRODUCT_SLUG = process.env.TEST_PRODUCT_SLUG || 'SP260706006287'
await page.goto(`/pdp/${PRODUCT_SLUG}`)
```

---

#### CS-04: Mailinator Integration — Dependency Eksternal Tidak Stabil
> **Risiko: TINGGI**

```groovy
// Keywords/utils/MailinatorHelper.groovy
WebUI.delay(3)  // beri waktu halaman stabil
// navigasi ke mailinator.com → klik email → switch iframe → extract OTP
```

Risiko:
- Mailinator rate-limit atau down → semua test registrasi/forgot password gagal
- Email OTP butuh waktu 5-30 detik muncul → delay tidak cukup atau berlebihan
- Iframe switching fragil di berbagai browser

**Solusi Playwright:**
```typescript
// Opsi 1: Gunakan Mailinator API (bukan UI browser)
const response = await request.get(`https://api.mailinator.com/api/v2/domains/public/inboxes/${inbox}`)
const otp = extractOTP(await response.json())

// Opsi 2: Gunakan nodemailer + SMTP test server (MailHog/Mailpit) untuk isolated testing
```

---

### 🟡 SEDANG — Code Smell yang Perlu Direfaktor

#### CS-05: Duplikasi Logic Helper

Dua keyword memiliki fungsi identik `generateRandomPhoneNumber()`:
- `AddressHelper.groovy` (line 51-58)
- `EditAddressHelper.groovy` (line 63-70)

**Solusi:** Satu class `DataFactory.ts` terpusat di Playwright.

---

#### CS-06: Dead Code / Commented-Out Code

```groovy
// Keywords/utils/MailinatorHelper.groovy
// Baris 88-171: SELURUH implementasi lama di-comment (83 baris!)
```

Kode yang di-comment menunjukkan riwayat refactoring yang tidak selesai dan menyulitkan pemeliharaan.

---

#### CS-07: Komentar Script Menyesatkan

```groovy
// Scripts/.../Login/Positive/Script1781170968189.groovy (line 20)
WebUI.comment('=== TC_Login_IncorrectPassword: Login gagal menggunakan account dan password yang benar ===')
```

Test case-nya adalah **Positive Login** tetapi komentarnya menyebutkan "IncorrectPassword" — ini menyesatkan.

---

#### CS-08: `scrollToPosition(0, 1500)` Hardcoded Pixel

```groovy
// Checkout script (line 86)
WebUI.scrollToPosition(0, 1500)
```

Nilai pixel absolut akan berbeda di resolusi layar yang berbeda → elemen mungkin tidak terlihat.

**Solusi Playwright:**
```typescript
await page.locator('[data-testid="payment-section"]').scrollIntoViewIfNeeded()
```

---

#### CS-09: `WebUI.switchToWindowIndex(1)` — Race Condition

```groovy
// Checkout script (line 140)
WebUI.delay(10)   // tunggu 10 detik
WebUI.switchToWindowIndex(1)
```

Switch window by index tidak reliable. Jika tab tidak terbuka dalam 10 detik → test gagal.

**Solusi Playwright:**
```typescript
// Playwright handle multi-tab secara native
const [newPage] = await Promise.all([
  context.waitForEvent('page'),
  page.click('button.checkout-btn')
])
await newPage.waitForLoadState()
```

---

### 🟢 RENDAH — Catatan Minor

| ID | Temuan | File |
|---|---|---|
| CS-10 | `SmartLocatorEnabled: false` di semua `.rs` object | Object Repository |
| CS-11 | `TC_Login_DataDriven` — `WebUI.setText` tanpa argumen kedua (kemungkinan bug) | `Script1781754294067.groovy` |
| CS-12 | Password hardcoded di `DummyData.groovy` & `RegistrationHelper.groovy` | Keywords |
| CS-13 | `BASIC` selector kosong di semua object (hanya andalkan XPath) | Object Repository |

---

## 4. 🔍 INVENTARIS CUSTOM KEYWORDS

| File | Package | Fungsi | Status untuk Playwright |
|---|---|---|---|
| `MyOrderKeyword.groovy` | `myorder` | Navigate Unpaid tab, click View Details, Cancel Order | ✅ Migrasi ke Page Object |
| `AddressHelper.groovy` | `utils` | Fill address form, generate random data | ✅ Migrasi ke Fixture/Factory |
| `BillingAddressHelper.groovy` | `utils` | Fill billing address form | ✅ Gabung dengan AddressHelper |
| `DeleteAddress.groovy` | `utils` | Delete shipping/billing address | ✅ Migrasi ke Page Object |
| `DummyData.groovy` | `utils` | Generate registration & address data | ✅ Migrasi ke `DataFactory.ts` |
| `EditAddressHelper.groovy` | `utils` | Edit delivery & billing address | ✅ Gabung dengan AddressHelper |
| `MailinatorHelper.groovy` | `utils` | Get OTP dari email Mailinator | ⚠️ Migrasi ke API call (bukan UI) |
| `MenuHoverHelper.groovy` | `utils` | JS-based hover + click submenu | ✅ Native di Playwright (`hover()`) |
| `ProductHelper.groovy` | `utils` | Loop dan klik produk available dari PLP | ✅ Migrasi ke Page Object |
| `RegistrationHelper.groovy` | `utils` | Registrasi akun baru + get OTP | ⚠️ Refaktor — pakai API OTP |
| `WindowHelper.groovy` | `utils` | Verify URL di tab baru lalu tutup | ✅ Native di Playwright |

> **Catatan:** Tidak ditemukan keyword untuk koneksi **Database** atau **API Setup** terpisah.
> Data-Driven menggunakan file **Excel** (`LoginData.xlsx`, `AddressData.xlsx`) yang akan dimigrasi ke **JSON fixtures**.

---

## 5. 📋 DAFTAR MODUL & PRIORITAS MIGRASI

| Prioritas | Modul | Alasan | Kompleksitas |
|---|---|---|---|
| 🔴 P1 | Authentication (Login, Reg, ForgotPwd) | Foundation semua test lain | Sedang |
| 🔴 P1 | Home & Navigation | Smoke test awal | Rendah |
| 🔴 P1 | Cart | Alur bisnis inti | Sedang |
| 🟠 P2 | Product (PDP, PLP, Category) | Prerequisite Checkout | Sedang |
| 🟠 P2 | Checkout (Full Flow + Midtrans) | Alur bisnis paling kompleks | Tinggi |
| 🟡 P3 | Address Management | Bergantung pada Login + Checkout | Sedang |
| 🟡 P3 | Guest Checkout | Sub-alur Checkout | Sedang |
| 🟡 P3 | TrackOrder | Fitur standalone | Rendah |
| 🟢 P4 | Email Verification | Bergantung sistem eksternal | Tinggi |
| 🟢 P4 | External Integration (WhatsApp) | UI verification | Rendah |
| 🟢 P4 | Content Verification | Copywriting check | Rendah |

---

## 6. 🏗️ STRATEGI ARSITEKTUR PLAYWRIGHT (ANTI-FLAKY)

### 6.1 Struktur Proyek yang Direkomendasikan

```
playwright-tests/
├── tests/
│   ├── auth/
│   │   ├── login.spec.ts
│   │   ├── registration.spec.ts
│   │   └── forgot-password.spec.ts
│   ├── cart/
│   ├── checkout/
│   ├── address/
│   ├── product/
│   ├── guest/
│   ├── track-order/
│   └── content-verification/
│
├── pages/                    # Page Object Model
│   ├── LoginPage.ts
│   ├── RegistrationPage.ts
│   ├── CartPage.ts
│   ├── CheckoutPage.ts
│   ├── AddressPage.ts
│   ├── ProductPage.ts
│   └── MyOrderPage.ts
│
├── fixtures/                 # Shared test setup
│   ├── auth.fixture.ts       # Login state management
│   └── index.ts
│
├── utils/
│   ├── DataFactory.ts        # Menggantikan DummyData.groovy
│   ├── MailinatorAPI.ts      # API-based email (bukan UI)
│   └── helpers.ts
│
├── data/                     # Test data (menggantikan Excel)
│   ├── login-scenarios.json
│   └── address-data.json
│
├── playwright.config.ts
├── global-setup.ts           # Login once, save storageState
└── .env                      # BASE_URL, credentials, API keys
```

### 6.2 Strategi Anti-Flaky (7 Pilar)

| # | Pilar | Implementasi |
|---|---|---|
| 1 | **No Hardcoded Sleep** | Ganti semua `delay()` dengan `waitForSelector`, `waitForResponse`, `expect().toBeVisible()` |
| 2 | **Shared Auth State** | `global-setup.ts` login sekali → simpan `storageState` → semua test reuse session |
| 3 | **Isolated Test Data** | Setiap test generate data unik via `DataFactory.ts` + timestamp |
| 4 | **API for Email OTP** | Mailinator/MailHog via REST API, bukan browser navigation |
| 5 | **Env-Based Config** | Semua URL, credential, dan config via `.env` — tidak ada hardcode |
| 6 | **Page Object Model** | Semua locator terpusat di page object — mudah maintain saat UI berubah |
| 7 | **Auto-Retry on Assertion** | Playwright built-in retry assertion (`expect`) mengeliminasi timing issues |

### 6.3 Pendekatan Locator (dari Object Repository)

Locator yang ada sudah cukup baik (semantic XPath), tapi harus dimigrasikan ke Playwright locator API:

```typescript
// KATALON (XPath)
'//button[contains(@class, "login_button")]//span[text()="Sign In"]'
'//input[@placeholder="Email"]'

// PLAYWRIGHT (lebih robust — prioritas urutan):
page.getByRole('button', { name: 'Sign In' })       // 1st choice: semantic
page.getByPlaceholder('Email')                       // 2nd choice: placeholder
page.locator('button.login_button span')             // 3rd: CSS selector
page.locator('//button[contains(@class, "login_button")]') // Last resort: XPath
```

---

## 7. 🔄 PLAN MIGRASI CI/CD

**Jenkins saat ini:** `Jenkinsfile` sudah baik dengan parameter BROWSER & PROFILE.

**Playwright CI:** Migrasi ke GitHub Actions atau tetap Jenkins:

```yaml
# .github/workflows/playwright.yml
jobs:
  test:
    runs-on: ubuntu-latest
    steps:
      - uses: actions/checkout@v4
      - uses: actions/setup-node@v4
      - run: npm ci
      - run: npx playwright install --with-deps
      - run: npx playwright test
      - uses: actions/upload-artifact@v4
        if: always()
        with:
          name: playwright-report
          path: playwright-report/
```

---

## 8. ⚠️ RISIKO & MITIGASI

| Risiko | Dampak | Mitigasi |
|---|---|---|
| Midtrans sandbox tidak stable | Test payment flaky | Mock Midtrans response + gunakan sandbox yang terkontrol |
| Mailinator rate-limit | Email test gagal | Gunakan Mailinator API key (premium) atau self-hosted Mailpit |
| Produk habis stok saat test | Cart/Checkout test gagal | Gunakan data produk fixture yang pasti tersedia (test data management) |
| Login shared account → konflik | Race condition jika parallel | Buat test user per modul (pool of test users) |
| Iframe Mailinator | Fragil di CI | Ganti full dengan API approach |

---

## 9. ✅ CHECKLIST PERSETUJUAN

Sebelum mulai menulis kode Playwright, harap konfirmasi:

- [ ] **Environment Target:** Development / Staging / Production?
- [ ] **Framework:** Playwright + TypeScript (direkomendasikan) atau JavaScript?
- [ ] **Mailinator:** Gunakan API key atau migrasi ke MailHog self-hosted?
- [ ] **Parallelism:** Berapa worker yang diinginkan di CI?
- [ ] **Reporting:** HTML report bawaan Playwright, atau integrasi Allure/Grafana?
- [ ] **Scope Fase 1:** Migrasi P1 saja dulu (Auth + Home + Cart) atau langsung full?

---

*Dokumen ini dibuat secara otomatis berdasarkan forensic scan proyek Katalon Studio "Pasti Ada Jalan" pada 2026-07-09.*
*Siap untuk dieksekusi setelah mendapat persetujuan.*
