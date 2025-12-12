package code;

import image.Pixel;
import image.APImage;

public class ImageManipulation {

    /** CHALLENGE 0: Display Image
     *  Write a statement that will display the image in a window
     */
    public static void main(String[] args) {
        vortexEffect("cyberpunk2077.jpg", 2.0, 0.9, 1000, 500, true, true);
    }

    /** CHALLENGE ONE: Grayscale
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a grayscale copy of the image
     *
     * To convert a colour image to grayscale, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * Set the red, green, and blue components to this average value. */
    public static void grayScale(String pathOfFile) {
        APImage image = new APImage(pathOfFile);
        for (Pixel pixel : image) {
            int average = getAverageColour(pixel);
            pixel.setRed(average);
            pixel.setGreen(average);
            pixel.setBlue(average);
        }
        image.draw();
    }

    /** A helper method that can be used to assist you in each challenge.
     * This method simply calculates the average of the RGB values of a single pixel.
     * @param pixel
     * @return the average RGB value
     */
    private static int getAverageColour(Pixel pixel) {
        return (pixel.getRed() + pixel.getGreen() + pixel.getBlue()) / 3;
    }

    /** CHALLENGE TWO: Black and White
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: a black and white copy of the image
     *
     * To convert a colour image to black and white, we need to visit every pixel in the image ...
     * Calculate the average of the red, green, and blue components of the pixel.
     * If the average is less than 128, set the pixel to black
     * If the average is equal to or greater than 128, set the pixel to white */
    public static void blackAndWhite(String pathOfFile) {
        APImage image = new APImage(pathOfFile);
        for (Pixel pixel : image) {
            int average = getAverageColour(pixel);
            if (average < 128) {
                pixel.setRed(0);
                pixel.setGreen(0);
                pixel.setBlue(0);
            } else {
                pixel.setRed(255);
                pixel.setGreen(255);
                pixel.setBlue(255);
            }
        }
        image.draw();
    }

    /** CHALLENGE Three: Edge Detection
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: an outline of the image. The amount of information will correspond to the threshold.
     *
     * Edge detection is an image processing technique for finding the boundaries of objects within images.
     * It works by detecting discontinuities in brightness. Edge detection is used for image segmentation
     * and data extraction in areas such as image processing, computer vision, and machine vision.
     *
     * There are many different edge detection algorithms. We will use a basic edge detection technique
     * For each pixel, we will calculate ...
     * 1. The average colour value of the current pixel
     * 2. The average colour value of the pixel to the left of the current pixel
     * 3. The average colour value of the pixel below the current pixel
     * If the difference between 1. and 2. OR if the difference between 1. and 3. is greater than some threshold value,
     * we will set the current pixel to black. This is because an absolute difference that is greater than our threshold
     * value should indicate an edge and thus, we colour the pixel black.
     * Otherwise, we will set the current pixel to white
     * NOTE: We want to be able to apply edge detection using various thresholds
     * For example, we could apply edge detection to an image using a threshold of 20 OR we could apply
     * edge detection to an image using a threshold of 35
     *  */
        public static void edgeDetection(String pathToFile, int threshold) {
        APImage image = new APImage(pathToFile);
        for(int x = 1; x < image.getWidth(); x++) {
            for(int y = 0; y < image.getHeight() - 1; y++) {
                Pixel currentP = image.getPixel(x, y);
                Pixel leftP = image.getPixel(x - 1, y);
                Pixel downP = image.getPixel(x, y + 1);
                if(Math.abs(getAverageColour(currentP) - getAverageColour(leftP)) < threshold ||
                   Math.abs(getAverageColour(currentP) - getAverageColour(downP)) < threshold) {
                    currentP.setRed(255);
                    currentP.setGreen(255);
                    currentP.setBlue(255);
                } else {
                    currentP.setRed(0);
                    currentP.setGreen(0);
                    currentP.setBlue(0);
                }
            }
        }
        image.draw();
    }

    /** CHALLENGE Four: Reflect Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image reflected about the y-axis
     *
     */
    public static void reflectImage(String pathToFile) {
        APImage image = new APImage(pathToFile);
        for(int x = 0; x < image.getWidth() / 2; x++) {
            for(int y = 0; y < image.getHeight(); y++) {
                Pixel leftP = image.getPixel(x, y);
                Pixel rightP = image.getPixel(image.getWidth() - 1 - x, y);
                Pixel tempP = leftP.clone();
                leftP.setRed(rightP.getRed());
                leftP.setGreen(rightP.getGreen());
                leftP.setBlue(rightP.getBlue());
                rightP.setRed(tempP.getRed());
                rightP.setGreen(tempP.getGreen());
                rightP.setBlue(tempP.getBlue());
            }
        }
        image.draw();
    }

    /** CHALLENGE Five: Rotate Image
     *
     * INPUT: the complete path file name of the image
     * OUTPUT: the image rotated 90 degrees CLOCKWISE
     *
     *  */
    public static void rotateImage(String pathToFile) {
        APImage image = new APImage(pathToFile);
        APImage rotatedImage = new APImage(image.getHeight(), image.getWidth());
        for(int x = 0; x < image.getWidth(); x++) {
            for(int y = 0; y < image.getHeight(); y++) {
                Pixel originalP = image.getPixel(x, y);
                Pixel rotatedP = rotatedImage.getPixel(image.getHeight()-1-y, x);
                rotatedP.setRed(originalP.getRed());
                rotatedP.setGreen(originalP.getGreen());
                rotatedP.setBlue(originalP.getBlue());
            }
        }
        rotatedImage.draw();
    }

public static void vortexEffect(String pathToFile, double strength, double radius, int centerX, int centerY, boolean counterClockwise, boolean showCenter) {
    APImage image=new APImage(pathToFile);
    APImage vortexImage=new APImage(image.getWidth(), image.getHeight());
    int width=image.getWidth();
    int height=image.getHeight();
    double maxRadius=Math.min(Math.min(centerX, centerY), Math.min(width-centerX, height-centerY));
    for(int x=0; x<width; x++) {
        for(int y=0; y<height; y++) {
            double dx = x-centerX;
            double dy = y-centerY;
            double distance=Math.sqrt(dx*dx+dy*dy);
            double angle=Math.atan2(dy, dx);
            double rotation=strength*(maxRadius-distance)/maxRadius;
            if(distance<maxRadius && counterClockwise) {
                double newAngle=angle+rotation;
                int srcX=(int)(centerX+distance*Math.cos(newAngle));
                int srcY=(int)(centerY+distance*Math.sin(newAngle));
                if(srcX>=0&&srcX<width&&srcY>=0&&srcY<height) {
                    Pixel srcPixel=image.getPixel(srcX, srcY);
                    Pixel destPixel=vortexImage.getPixel(x, y);
                    destPixel.setRed(srcPixel.getRed());
                    destPixel.setGreen(srcPixel.getGreen());
                    destPixel.setBlue(srcPixel.getBlue());
                }
            } else if(distance<maxRadius && !counterClockwise) {
                double newAngle=angle-rotation;
                int srcX=(int)(centerX+distance*Math.cos(newAngle));
                int srcY=(int)(centerY+distance*Math.sin(newAngle));
                if(srcX>=0&&srcX<width&&srcY>=0&&srcY<height) {
                    Pixel srcPixel=image.getPixel(srcX, srcY);
                    Pixel destPixel=vortexImage.getPixel(x, y);
                    destPixel.setRed(srcPixel.getRed());
                    destPixel.setGreen(srcPixel.getGreen());
                    destPixel.setBlue(srcPixel.getBlue());
                }
            } else {
                Pixel srcPixel=image.getPixel(x, y);
                Pixel destPixel=vortexImage.getPixel(x, y);
                destPixel.setRed(srcPixel.getRed());
                destPixel.setGreen(srcPixel.getGreen());
                destPixel.setBlue(srcPixel.getBlue());
            }
        }
    }
    if(showCenter) {
        Pixel centerPixel=vortexImage.getPixel(centerX, centerY);
        centerPixel.setRed(255);
        centerPixel.setGreen(0);
        centerPixel.setBlue(0);
        centerPixel=vortexImage.getPixel(centerX+1, centerY);;
        centerPixel.setRed(255);
        centerPixel.setGreen(0);
        centerPixel.setBlue(0);
        centerPixel=vortexImage.getPixel(centerX-1, centerY);;
        centerPixel.setRed(255);
        centerPixel.setGreen(0);
        centerPixel.setBlue(0);
        centerPixel=vortexImage.getPixel(centerX, centerY+1);;
        centerPixel.setRed(255);
        centerPixel.setGreen(0);
        centerPixel.setBlue(0);
        centerPixel=vortexImage.getPixel(centerX, centerY-1);;
        centerPixel.setRed(255);
        centerPixel.setGreen(0);
        centerPixel.setBlue(0);
    }
    vortexImage.draw();
}
}
