📄 Available languages: [English](README.md) | [Українська](README.ua.md)

# 🦎 Axologger

**Axologger** is a universal CLI tool that collects source code files from any project into a single, readable Markdown document.

It works with any text-based source files: `.java`, `.py`, `.js`, `.xml`, `.html`, `.txt`, and more.  
Perfect for archiving, code reviews, preparing publications, or sending code to ChatGPT (or any AI assistant) 😄

---

## ⚙️ Features

- ✅ Filter files by extension: `--types .py .java .html`
- ✅ Recursively collect from folders: `--dir src test scripts`
- ✅ Add specific files manually: `--files main.py utils.java`
- ✅ Exclude unwanted folders: `--exclude-dir target build .git`
- ✅ Export everything to Markdown: `--out output.md`
- ✅ Dry-run mode to simulate: `--dry-run`
- ✅ Save reusable configs in: `--prop-file`
- ✅ Multi-language help: `--help uk | en`
- ✅ Fully portable (pure Java, no dependencies)

---

## 🚀 Installation

1. Download the latest [release from GitHub](https://github.com/axolotl1k/Axologger/releases)
2. Unpack `axologger-dist.zip` to a convenient location (e.g. `C:\MyTools\Axologger`)
3. Run `install_axologger.bat` to add the `bin/` directory to your system PATH
4. Open a new terminal and run:
```bash
axologger --help
```

---

## 🔍 Examples

```bash
# Collect all .py and .java files from src/ and test/
axologger --types .py .java --dir src test --out code.md

# Add individual files manually
axologger --files App.java script.py README.md

# Preview what would be collected (without writing)
axologger --types .java --dir src --dry-run

# Load configuration from a preset file
axologger --prop-file config.txt
```

---

## 📁 Markdown Output Format

Files are written as:

````md
## ./src/main.py
```py
# code here
```
````

> File extension is automatically used as the language identifier.

---

## 📜 License

MIT

---

👤 Author: [@axolotlik](https://github.com/axolotl1k)
