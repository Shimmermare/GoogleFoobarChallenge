/*
 * MIT License
 *
 * Copyright (c) 2019 Shimmermare <shimmermare@gmail.com>
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.shimmermare.misc.googlefoobarchallenge.level4;

import org.apache.batik.dom.GenericDOMImplementation;
import org.apache.batik.svggen.SVGGraphics2D;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;

import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Typical gamedev task: so simple but at the same time hard as nuts.
 * It seems impossible to calculate multiple reflections between your and guard positions,
 * unless you think about the reflective surface as of mirrored copy of the room.
 * So, these two are the same thing: https://photos.app.goo.gl/pKDjw2HhH1khQ9WU8
 * <p>
 * It took me a few days to notice the fact
 * and only an hour (or 3 if you count failed attempt with Bresenham's line algorithm) to implement solution.
 * <p>
 * Batik (https://mvnrepository.com/artifact/org.apache.xmlgraphics/batik-all) is required to output SVG.
 */
public class BringingAGunToAGuardFight {
    public static int solution(int[] dimensions, int[] yourPos, int[] guardPos, int maxDistance) {
        int dimX = dimensions[0], dimY = dimensions[1];
        int youX = yourPos[0], youY = yourPos[1];
        int guardX = guardPos[0], guardY = guardPos[1];

        if (dimX < 1 || dimX > 1250 || dimY < 1 || dimY > 1250)
            throw new IllegalArgumentException("Dimensions outside of 1 to 1250 range");
        if (youX < 1 || youX >= dimX || youY < 1 || youY >= dimY)
            throw new IllegalArgumentException("Your pos is outside of room");
        if (guardX < 1 || guardX >= dimX || guardY < 1 || guardY >= dimY)
            throw new IllegalArgumentException("Guard pos is outside of room");
        if (maxDistance <= 1 || maxDistance > 10000)
            throw new IllegalArgumentException("Max distance is outside of 1 to 10000 range");

        // Bounding rect
        int tilesMinusX = (int) Math.ceil((maxDistance - youX) / (float) dimX);
        int tilesPlusX = (int) Math.ceil((maxDistance - dimX + youX) / (float) dimX);
        int tilesMinusY = (int) Math.ceil((maxDistance - youY) / (float) dimY);
        int tilesPlusY = (int) Math.ceil((maxDistance - dimY + youY) / (float) dimY);
        int totalTiles = (tilesMinusX + tilesPlusX + 1) * (tilesMinusY + tilesPlusY + 1);

        SVGWrapper svg = new SVGWrapper(maxDistance < 1000 ? 1000.0F / maxDistance : 1.0F);
        // Not much use from grid on big res
        if (maxDistance <= 100) {
            svg.addGrid(0, Color.LIGHT_GRAY, -tilesMinusX * dimX, -tilesMinusY * dimY,
                    tilesPlusY * dimX + dimX, tilesPlusY * dimY + dimY);
        }
        svg.addEllipse(10, Color.RED, youX, youY, maxDistance * 2, maxDistance * 2);

        // Directions from your origin pos to tile's your pos
        // If more than two occurrences lie on the same line, the nearest one is picked.
        Map<Vector2, Integer> dirsToYou = new HashMap<>(totalTiles);
        for (int tileX = -tilesMinusX; tileX <= tilesPlusX; tileX++) {
            for (int tileY = -tilesMinusY; tileY <= tilesPlusY; tileY++) {
                // Mirror pattern; Origin is (0, 0) so mirror if odd tile
                int tileYouX = (tileX & 1) == 0 ? youX + tileX * dimX : (dimX - youX) + tileX * dimX;
                int tileYouY = (tileY & 1) == 0 ? youY + tileY * dimY : (dimY - youY) + tileY * dimY;
                int vecX = tileYouX - youX;
                int vecY = tileYouY - youY;

                svg.addRect(20, Color.BLACK, tileX * dimX, tileY * dimY, dimX, dimY);
                svg.addText(21, Color.BLACK, tileX * dimX, tileY * dimY, "[" + tileX + ", " + tileY + "]");
                svg.addPoint(30, Color.MAGENTA, tileYouX, tileYouY);
                svg.addText(31, Color.MAGENTA, tileYouX, tileYouY, "[" + tileYouX + ", " + tileYouY + "]");

                int distanceSqr = vecX * vecX + vecY * vecY;
                if (distanceSqr > maxDistance * maxDistance) continue;
                Vector2 vec = new Vector2(vecX, vecY);
                vec.normalize();

                int currentDistSqr = dirsToYou.getOrDefault(vec, -1);
                if (currentDistSqr == -1 || distanceSqr < currentDistSqr) {
                    dirsToYou.put(vec, distanceSqr);
                }
            }
        }

        // Using map is completely unnecessary since HashSet can't store the same value twice,
        // But I did visualisation too so I needed distance.
        Map<Vector2, Integer> dirsToTarget = new HashMap<>(totalTiles);
        for (int tileX = -tilesMinusX; tileX <= tilesPlusX; tileX++) {
            for (int tileY = -tilesMinusY; tileY <= tilesPlusY; tileY++) {
                // Mirror pattern; Origin is (0, 0) so mirror if odd tile
                int tileGuardX = (tileX & 1) == 0 ? guardX + tileX * dimX : (dimX - guardX) + tileX * dimX;
                int tileGuardY = (tileY & 1) == 0 ? guardY + tileY * dimY : (dimY - guardY) + tileY * dimY;
                int vecX = tileGuardX - youX;
                int vecY = tileGuardY - youY;

                svg.addPoint(40, Color.BLUE, tileGuardX, tileGuardY);
                svg.addText(41, Color.BLUE, tileGuardX, tileGuardY, "[" + tileGuardX + ", " + tileGuardY + "]");

                int distanceSqr = vecX * vecX + vecY * vecY;
                if (distanceSqr > maxDistance * maxDistance) continue;
                Vector2 vec = new Vector2(vecX, vecY);
                vec.normalize();

                // Check for self-hit
                int obstacleSelfDistSqr = dirsToYou.getOrDefault(vec, -1);
                if (obstacleSelfDistSqr == -1 || distanceSqr < obstacleSelfDistSqr) {
                    // Check for non-target guard hit
                    int currentDistSqr = dirsToTarget.getOrDefault(vec, -1);
                    if (currentDistSqr == -1 || distanceSqr < currentDistSqr) {
                        dirsToTarget.put(vec, distanceSqr);
                    }
                }
            }
        }

        dirsToTarget.forEach((k, v) -> {
            double dist = Math.sqrt(v);
            svg.addLine(50, Color.CYAN, youX, youY, (float) (youX + k.x * dist), (float) (youY + k.y * dist));
        });

        try {
            Path svgFile = Paths.get("(" + dimX + "," + dimY + ")_(" + youX + "," + youY + ")_(" + guardX + "," + guardY + ")_" + maxDistance + ".svg");
            if (Files.notExists(svgFile)) Files.createFile(svgFile);
            Writer writer = Files.newBufferedWriter(svgFile);
            svg.write(writer);
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dirsToTarget.size();
    }

    // Mutable
    private static final class Vector2 {
        private static final double PRECISION = 10000000.0D;

        private double x, y;

        Vector2(double x, double y) {
            this.x = x;
            this.y = y;
        }

        void normalize() {
            if (x == 0.0D && y == 0.0D) return;
            // TODO replace with FISR if precision allows
            double distance = Math.sqrt(x * x + y * y);
            // Round a little to avoid precision errors
            x = (Math.round(x / distance * PRECISION) / PRECISION);
            y = (Math.round(y / distance * PRECISION) / PRECISION);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Vector2 vector2 = (Vector2) o;
            return Double.compare(vector2.x, x) == 0 &&
                    Double.compare(vector2.y, y) == 0;
        }

        @Override
        public int hashCode() {
            return Double.hashCode(x) * 31 + Double.hashCode(y);
        }
    }

    private static class SVGWrapper {
        private final float scale;

        private final List<SVGElement> elements = new ArrayList<>();

        SVGWrapper(float scale) {
            this.scale = scale;
        }

        void addGrid(int layer, Color color, int xMinus, int yMinus, int xPlus, int yPlus) {
            for (int x = xMinus; x <= xPlus; x++) {
                addLine(layer, color, x, yMinus, x, yPlus);
            }
            for (int y = yMinus; y <= yPlus; y++) {
                addLine(layer, color, xMinus, y, xPlus, y);
            }
        }

        void addEllipse(int layer, Color color, float centerX, float centerY, float width, float height) {
            centerX *= scale;
            centerY *= scale;
            width *= scale;
            height *= scale;
            elements.add(new SVGShape(layer, color, new Ellipse2D.Float(centerX - width / 2.0F, centerY - height / 2.0F, width, height)));
        }

        void addLine(int layer, Color color, float startX, float startY, float endX, float endY) {
            startX *= scale;
            startY *= scale;
            endX *= scale;
            endY *= scale;
            elements.add(new SVGShape(layer, color, new Line2D.Float(startX, startY, endX, endY)));
        }

        void addPoint(int layer, Color color, float x, float y) {
            x *= scale;
            y *= scale;
            elements.add(new SVGShape(layer, color, new Rectangle2D.Float(x - 1.0F, y - 1.0F, 2, 2)));
        }

        void addRect(int layer, Color color, float x, float y, float width, float height) {
            x *= scale;
            y *= scale;
            width *= scale;
            height *= scale;
            elements.add(new SVGShape(layer, color, new Rectangle2D.Float(x, y, width, height)));
        }

        void addText(int layer, Color color, float x, float y, String text) {
            x *= scale;
            y *= scale;
            elements.add(new SVGText(layer, x, y, color, text));
        }

        void write(Writer writer) throws IOException {
            DOMImplementation dom = GenericDOMImplementation.getDOMImplementation();
            Document document = dom.createDocument("http://www.w3.org/2000/svg", "svg", null);
            SVGGraphics2D graphics = new SVGGraphics2D(document);
            elements.sort(Comparator.comparingInt(e -> e.layer));
            for (SVGElement element : elements) element.draw(graphics);
            graphics.stream(writer, true);
        }

        private static abstract class SVGElement {
            private final int layer;

            protected final Color color;

            SVGElement(int layer, Color color) {
                this.layer = layer;
                this.color = color;
            }

            public abstract void draw(SVGGraphics2D graphics);
        }

        private static class SVGShape extends SVGElement {
            private final Shape shape;

            SVGShape(int layer, Color color, Shape shape) {
                super(layer, color);
                this.shape = shape;
            }

            @Override
            public void draw(SVGGraphics2D graphics) {
                graphics.setColor(color);
                graphics.draw(shape);
            }
        }

        private static class SVGText extends SVGElement {
            private final float x, y;
            private final String text;

            SVGText(int layer, float x, float y, Color color, String text) {
                super(layer, color);
                this.x = x;
                this.y = y;
                this.text = text;
            }

            @Override
            public void draw(SVGGraphics2D graphics) {
                graphics.setColor(color);
                graphics.drawString(text, x, y + 15);
            }
        }
    }
}
