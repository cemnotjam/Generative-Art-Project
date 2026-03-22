<<<<<<< HEAD
# Generative-Art-Project
![Glitch Art Engine Demo](ScreenShots/demo.png)
=======
# Glitch Art Engine

A desktop application built with Java and JavaFX that transforms any portrait photo into a glitch art piece inspired by physical collage techniques.

## What it does

The app takes a normal photo and runs it through a four-step generative process:

**Step 1 — Grayscale conversion**
The photo is converted to grayscale by averaging the red, green and blue channel values of every pixel. This is done by directly reading and writing raw pixel data using JavaFX's `PixelReader` and `PixelWriter` classes.

**Step 2 — 2x2 mirror grid**
The grayscale image is duplicated into a 2x2 grid. The top two copies are the original. The bottom two are flipped upside down. This creates a symmetrical composition before the shuffling begins.

**Step 3 — Vertical strip shuffle**
The grid is cut into vertical strips. The strips are then reassembled by interleaving from the left and right sides simultaneously — strip 1, strip 8, strip 2, strip 7, strip 3, strip 6, and so on. This is a digital recreation of the physical paper-cutting collage technique where printed strips are manually rearranged.

**Step 4 — Horizontal strip shuffle**
The same interleaving process is applied to horizontal strips — top and bottom strips are woven together. The combination of both passes creates the final fragmented portrait effect.

The strip size is adjustable via a slider in the UI, giving different visual results from fine grain to large block patterns.

## Tech stack

- Java 25
- JavaFX 26
- VS Code

## How to run

1. Install JDK 21+ and JavaFX SDK
2. Clone the repository
3. Run `./run.sh`
>>>>>>> 1b9a4a7 (Glitch Art Engine)
