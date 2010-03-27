/*
 *  BlinzCore - core library of audio, video, and other essential classes.
 *  Copyright (C) 2009  BlinzProject <gtalent2@gmail.com>
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License version 3 as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.blinz.graphics;

import com.sun.opengl.util.texture.Texture;
import com.sun.opengl.util.texture.TextureIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 * Contains the objects used to implement image support.
 * @author Blinz Project
 */
final class ImageStub {

    static final int LOCAL = 0;
    static final int HTTP = 1;
    /**
     * Denotes the type of source to be used for this stub, local or remote.
     */
    int type = LOCAL;
    int dependentCount = 1;
    private String path;
    private Texture texture;
    private int width, height;
    private BufferedImage bufferedImage;

    ImageStub(String path, int type) throws IOException {
        this.path = path;
        this.type = type;
        load();
    }

    /**
     *
     * @return the path to this image.
     */
    final String getPath() {
        return path;
    }

    /**
     *
     * @return the width of the image this ImageStub represents.
     */
    final int getWidth() {
        return width;
    }

    /**
     *
     * @return the height of the image this ImageStub represents.
     */
    final int getHeight() {
        return height;
    }

    /**
     *
     * @return the JOGL Texture representation of the image this ImageStub represents.
     */
    final Texture getTexture() {
        if (texture == null) {
            if (bufferedImage == null) {
                try {
                    if (type == LOCAL) {
                        bufferedImage = ImageIO.read(new File(path));
                    } else {
                        bufferedImage = ImageIO.read(new URL(path));
                    }
                } catch (IOException ex) {
                    Logger.getLogger(ImageStub.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            texture = (Texture) TextureIO.newTexture(bufferedImage, true);
            bufferedImage = null;
        }
        return texture;
    }

    /**
     * Dumps the object representing the image.
     */
    final void dumpImage() {
        texture = null;
        bufferedImage = null;
    }

    /**
     * Loads the image from storage.
     * @throws IOException
     */
    private final void load() throws IOException {
        if (type == LOCAL) {
            bufferedImage = ImageIO.read(new File(path));
        } else {
            bufferedImage = ImageIO.read(new URL(path));
        }
        width = bufferedImage.getWidth();
        height = bufferedImage.getHeight();
        if (ScreenManager.isInitialized()) {
            bufferedImage = null;
        }
    }
}