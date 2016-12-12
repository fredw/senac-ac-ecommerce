package com.ddf.controller;


import com.ddf.Application;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;

@Controller
public class ImageController {

    @RequestMapping(value = "/image/{image:.+}")
    @ResponseBody
    public byte[] show(
        @PathVariable(value = "image") String image,
        @RequestParam(value = "width", required = false) Integer width,
        @RequestParam(value = "height", required = false) Integer height
    ) throws IOException {

        // Output and source image
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        BufferedImage sourceImage = ImageIO.read(new File(Application.UPLOAD_PATH + image));

        // Create the thumbnailator builder
        Thumbnails.Builder<BufferedImage> imageBuilder = Thumbnails.of(sourceImage);

        // Scale if inform width/height
        if (width != null && height != null) {
            imageBuilder.size(width, height).crop(Positions.CENTER);
        } else {
            imageBuilder.size(sourceImage.getWidth(), sourceImage.getHeight());
        }

        // Get buffered image
        BufferedImage bufferedImage = imageBuilder
            .outputFormat("jpeg")
            .outputQuality(0.8)
            .asBufferedImage();

        String extension = "jpg";
        if (image.endsWith(".png")) {
            extension = "png";
        }

        // Output
        ImageIO.write(bufferedImage, extension, outputStream);
        return outputStream.toByteArray();
    }
}
