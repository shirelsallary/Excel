# Spreadsheet Engine (Java)

A simplified spreadsheet system implemented in **Java**.

The project supports cells containing **numbers, text, and formulas** with references to other cells (e.g. `=A0+3`).
Formulas are evaluated using a **dependency depth algorithm** that ensures cells are computed in the correct order while detecting **circular references and invalid formulas**.

### Features

* Arithmetic formulas (`+ - * /`)
* Cell references (e.g. `A0`, `B3`)
* Dependency resolution between cells
* Circular reference detection
* Simple GUI visualization
* Save / Load spreadsheet data
* JUnit tests for edge cases

### Example

```text id="2y7ue8"
A0 = 5
A1 = =A0+3
A2 = =(A1+2)*2
```

Result:

```text id="x94glo"
A0 → 5
A1 → 8
A2 → 20
```
