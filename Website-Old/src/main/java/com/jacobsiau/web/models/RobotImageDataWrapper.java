package com.jacobsiau.web.models;

import java.util.List;

/**
 * RobotImageDataWrapper is a wrapper class for a list of {@link RobotImageData} objects associated with a position so we can send/recieve these as a list via the controller methods.
 */
public class RobotImageDataWrapper {

    private Double xpos;
    private Double ypos;
    private List<RobotImageData> imageData;

    public RobotImageDataWrapper() {
        super();
    }

    public RobotImageDataWrapper(Double xpos, Double ypos, List<RobotImageData> imageData) {
        super();
        this.xpos = xpos;
        this.ypos = ypos;
        this.imageData = imageData;
    }

    /**
     * @return the xpos
     */
    public Double getXpos() {
        return xpos;
    }

    /**
     * @param xpos the xpos to set
     */
    public void setXpos(Double xpos) {
        this.xpos = xpos;
    }

    /**
     * @return the ypos
     */
    public Double getYpos() {
        return ypos;
    }

    /**
     * @param ypos the ypos to set
     */
    public void setYpos(Double ypos) {
        this.ypos = ypos;
    }

   /**
    * @return the imageData
    */
   public List<RobotImageData> getImageData() {
       return imageData;
   }

   /**
    * @param imageData the imageData to set
    */
   public void setImageData(List<RobotImageData> imageData) {
       this.imageData = imageData;
   }
}
