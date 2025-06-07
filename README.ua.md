📄 Мови: [English](README.md) | [Українська](README.ua.md)


# 🦎 Axologger

**Axologger** — універсальна CLI-утиліта для збору вихідних кодів з проєктів у один Markdown-файл.

Працює з будь-якими текстовими файлами: `.java`, `.py`, `.js`, `.xml`, `.html`, `.txt`, тощо.  
Ідеально підходить для архівації, ревʼю, підготовки до публікацій або для надсилання в ChatGPT (чи інший ШІ асистент) 😄

---

## ⚙️ Можливості

- ✅ Вибір файлів за розширенням: `--types .py .java .html`
- ✅ Вибір директорій: `--dir src test scripts`
- ✅ Додавання конкретних файлів: `--files main.py utils.java`
- ✅ Виключення директорій: `--exclude-dir target build .git`
- ✅ Запис у Markdown: `--out output.md`
- ✅ Режим dry-run: `--dry-run` (тільки перегляд)
- ✅ Збереження конфігурацій: `--prop-file`
- ✅ Довідка українською або англійською: `--help uk | en`
- ✅ Повністю автономна (Java, без бібліотек)

---

## 🚀 Встановлення

1. Завантаж останній [реліз з GitHub](https://github.com/axolotl1k/Axologger/releases)
2. Розпакуй `axologger-dist.zip` у зручне місце (наприклад: `C:\MyTools\Axologger`)
3. Запусти `install_axologger.bat` — це додасть `bin/` до системного PATH
4. Відкрий новий термінал — і тепер можеш запускати утиліту просто як:
```bash
axologger --help
```

---

## 🔍 Приклади використання

```bash
# Витягнути всі Python та Java файли з src/ і test/
axologger --types .py .java --dir src test --out code.md

# Додати тільки конкретні файли вручну
axologger --files App.java script.py README.md

# Dry-run: перевірити що включиться
axologger --types .java --dir src --dry-run

# Використати попередньо підготовлений набір параметрів
axologger --prop-file config.txt
```

---

## 📁 Формат Markdown-виводу

Файли зберігаються у вигляді:

````md
## ./src/main.py
```py
# code here
```
````

> Розширення автоматично додається як тип `code block`.

---

## 📜 Ліцензія

MIT

---

👤 Автор: [@axolotlik](https://github.com/axolotl1k)
