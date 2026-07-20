# Antigravity 2.2.1 Migration Rules: Katalon to Playwright

## 1. Aturan Konversi Object Repository (.rs / XML) -> Page Object Model (.ts)
- Baca file di `Object Repository/`. KATALON sering menggunakan XPath absolut atau CSS yang rapuh.
- **DILARANG** menyalin langsung XPath dari Katalon jika tidak terpaksa.
- Ekstrak atribut seperti `name`, `id`, `placeholder`, atau `text` dari file XML `.rs` Katalon, lalu ubah menjadi **Playwright Semantic Locator** (`getByRole`, `getByText`, `getByTestId`).
- Buatkan class Page Object Model (POM) TypeScript untuk setiap folder halaman di `playwright-tests/pages/`.

## 2. Aturan Konversi Script Groovy -> Playwright Spec (.spec.ts)
- Gunakan mapping berikut untuk menerjemahkan method Katalon `WebUI`:
  - `WebUI.openBrowser(url)` / `WebUI.navigateToUrl(url)` -> `await page.goto(url)`
  - `WebUI.click(findTestObject(...))` -> `await this.page.getByRole(...).click()`
  - `WebUI.setText(findTestObject(...), 'text')` -> `await this.page.getByRole(...).fill('text')`
  - `WebUI.verifyElementVisible(...)` -> `await expect(...).toBeVisible()`
  - `WebUI.delay(5)` -> **HAPUS/ABAIKAN**. Ganti dengan explicit/auto-wait Playwright.
  - `WebUI.callTestCase(...)` -> Ubah menjadi fungsi modular / pemanggilan method POM.

## 3. Custom Keywords
- Jika menemukan pemanggilan Custom Keywords Katalon (di folder `Keywords/`), refactor logika tersebut menjadi **Playwright Custom Fixtures** atau *utility functions* di folder `playwright-tests/utils/`.

## 4. Aturan Penulisan Nama Test Case (Test Naming Convention)
- Setiap blok pengujian `test('...', async () => {})` WAJIB diawali dengan tag penanda jenis pengujian: **`[POSITIVE]`** atau **`[NEGATIVE]`**.
- Setelah tag tipe, masukkan format nama/ID Test Case asli dari Katalon (contoh: `TC_001_Success_Login`), lalu diikuti dengan tanda hubung `-` dan deskripsi singkat dalam bahasa Indonesia atau Inggris sesuai skrip asli.
- **Format Wajib:** `test('[POSITIVE/NEGATIVE] <ID_Katalon> - <Deskripsi>', async ({ page }) => { ... })`
- **Contoh Benar:** `test('[NEGATIVE] TC_005_Empty_Username - Error validation harus muncul saat username kosong', ...)`
- **DILARANG:** Menulis nama test case tanpa awalan `[POSITIVE]` atau `[NEGATIVE]`.