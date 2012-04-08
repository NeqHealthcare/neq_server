package eu.neq.mais.domain.gnuhealth;

import eu.neq.mais.domain.Document;
import sun.misc.BASE64Decoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: user
 * Date: 15.03.12
 * Time: 11:58
 * To change this template use File | Settings | File Templates.
 */
public class DocumentGnu implements Document {
    String User;
    int id;
    DateGnu lastEdited;
    String description;
    DataGnu data;


    public DataGnu getData() {
        return data;
    }

    public void setData(DataGnu data) {
        this.data = data;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DateGnu getLastEdited() {
        return lastEdited;
    }

    public void setLastEdited(DateGnu lastEdited) {
        this.lastEdited = lastEdited;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }

    public byte[] getImage() {

        byte[] img = null;

        if (data != null) {


            try {
                img = new BASE64Decoder().decodeBuffer(getData().getBase64());
            } catch (IOException e) {
                e.printStackTrace();
                //To change body of catch statement use File | Settings | File Templates.
            }


        }

        return img;
    }

    public byte[] getThumbNail() {
        byte[] img = this.getImage();
        byte[] out = null;
        ByteArrayOutputStream byteArrayOut = new ByteArrayOutputStream();


        try {
            BufferedImage bufferedImage = ImageIO.read(new ByteArrayInputStream(img));
            int w = bufferedImage.getWidth() / 10;
            int h = bufferedImage.getHeight() / 10;
            BufferedImage after = new BufferedImage(w, h, bufferedImage.getType());
            AffineTransform at = new AffineTransform();
            at.scale(0.1, 0.1);
            AffineTransformOp scaleOp =
                    new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
            after = scaleOp.filter(bufferedImage, after);
            ImageIO.write(after, "jpg", byteArrayOut);
            out = byteArrayOut.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        return out;

    }


}
