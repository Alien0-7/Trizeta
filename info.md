# Code Style Rules

Rules to follow when writing code on this project ro ensure an aligned style and prevent issues during code merges

## 1. Identation

---
Use `TABS`, no spaces

Right example:
```
if (x < y) {  // One space between ")" and "{"
    x = y;     // Indent one additional TAB relative to the "if" statement
    y = 0;
} else {      // One space before and after the "else" keyword
    x = 0;
    y = y/2;
}
```

Wrong examples:
```
if (x < y)            if (x < y)             if (x < y){
{                     {                          x = y;
    x = y;                x = y;                 y = 0;
    y = 0;                y = 0;             }
}                     } else                 else {
else                  {                          x = 0;
{                           x = 0;               y = y/2;
    x = 0;                  y = y/2;         }
    y = y/2;           }
}  
```

## 2. Java code formatting

---
Braces are used with if, else, for, do and while statements, even when the body is empty or contains only a single statement.
Right examples:
```
if (username == null) {       if (username == null) {
    return false;                 //TODO ...
}                             }
```

Wrong examples:
```
if (username == null)       if (username == null)
    return false;               //TODO ...

```

## 3. Naming Conventions

---
Use "CamelCase", no other conventions.

Right examples:
```
    int thisIsAnExampleVariable = 10;
    thisIsAnExampleFunction();
```

Wrong examples:
```
    int this_is_an_example_variable = 10;
    thisisanexamplefunction();
```

## 4. Code organization

---

1. All variables declarations go at the beginning of the class.
2. Keep methods short.
3. Use returns to simplify method structure.
4. Put the shorter of the then-part and else-part first (remove the else block if it's not needed).
5. Declare local variables as close as possible to their first use.

## 5. COMMENT as much as possible.

---
Add comments to statements that aren’t simple,
so other developers can understand or fix them more quickly.