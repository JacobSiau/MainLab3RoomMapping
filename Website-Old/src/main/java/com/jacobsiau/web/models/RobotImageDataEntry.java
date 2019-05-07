package com.jacobsiau.web.models;

/**
 * RobotImageData is a POJO to represent entries in the ROBOT_IMAGE_DATA table.
 */
public class RobotImageDataEntry {

    private Long id;
    private Double xpos;
    private Double ypos;
    private Double depth;


    public RobotImageDataEntry() {
        super();
    }

    public RobotImageDataEntry(Long id, Double xpos, Double ypos, Double depth) {
        super();
        this.id = id;
        this.xpos = xpos;
        this.ypos = ypos;
        this.depth = depth;
    }

    public RobotImageDataEntry(Double xpos, Double ypos, Double depth) {
        super();
        this.xpos = xpos;
        this.ypos = ypos;
        this.depth = depth;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    
    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
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
     * @return the depth
     */
    public Double getDepth() {
        return depth;
    }

    /**
     * @param depth the depth to set
     */
    public void setDepth(Double depth) {
        this.depth = depth;
    }
}
