# Graphing Calculator

## ▸ About 📖

This repository contains the source code of my **graphing calculator** written in Java with JavaFX, originally built as a pair project for a university course. This isn’t intended to be the next Desmos or anything — just something I want to continue developing for fun. The current version is as submitted for the project, with no changes — but I do plan on cleaning it up and adding more features.

You can download the compiled runnable `.jar` file [here](https://github.com/haminic/graphing-calculator/releases/latest) (Java 21+).

## ▸ Features 📈

Some things you can do with the calculator:

- **Four input modes**:
    - **Evaluation**: Input evaluable expressions, like `"4 + sin(pi)"` to get numerical results.
    - **Graphing**: Input expressions in terms of `x`, like `"x^2 + 3x + 2"` to plot them.
    - **Constant Definition**: Define constants, like `"a = 5 + pi"`, and reuse them.
    - **Function Definition**: Define functions, like `"f(x) = sin(x) + x^2"` that can be graphed and reused.

- **Expression parsing**:
    - Supports implicit multiplication: `(x + 3)(x + 2)`
    - Supports shorthand like `sin x` and powers like `sin^2 x`
    - Built-in constants: `e`, `pi`
    - Built-in functions: `sin`, `cos`, `tan`, `log`, `sqrt`, `arcsin`, `floor`, etc.

- **Graph controls**:
    - Toggle individual function visibility and color.
    - Zoom and pan via buttons or edge-clicks.
    - Quick access to "Reset Zoom" and "To Origin".

For more information, refer to the 'Help' menu in the app, or see [`guide.txt`](/src/main/resources/guide/guide.txt).

## ▸ Archive 📦

This repo also includes the documentation and the UML diagram as submitted for the project, [here](submission). These will not be maintained as the code evolves.

## ▸ Credits 💳

Originally built with early contributions from [boonpasin](https://github.com/boonpasin) as a pair project for *Programming Methodology* course at *Chulalongkorn University*.
