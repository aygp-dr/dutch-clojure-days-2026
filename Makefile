.PHONY: all tangle diagrams clean help

# Default target
all: tangle diagrams lint

# Lint Org files
lint:
	@echo "Linting org files..."
	@./scripts/lint-org.bb

# Tangle all Org files to source code
tangle:
	@echo "Tangling org files..."
	@./scripts/tangle-all.bb

# Generate Mermaid diagrams from Org files
diagrams:
	@echo "Generating diagrams..."
	@./scripts/generate-diagrams.bb

# Clean generated artifacts (optional, adjust paths as needed)
clean:
	@echo "Cleaning generated files..."
	@rm -rf presentations/assets/diagrams/*
	@# Add more clean steps if needed, e.g. removing tangled files

# Show help
help:
	@echo "Available targets:"
	@echo "  all       - Tangle code and generate diagrams (default)"
	@echo "  tangle    - Extract source code from Org files"
	@echo "  diagrams  - Generate Mermaid diagrams"
	@echo "  clean     - Remove generated artifacts"
