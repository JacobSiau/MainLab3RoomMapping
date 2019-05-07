package com.jacobsiau.web.models;

/**
 * RobotData is a POJO to represent entries in the ROBOT_DATA table.
 */
public class RobotData {

    private Long id;
    private String xpos;
    private String ypos;
    private String orientation;
    private String depth;

    public RobotData() {
        super();
    }

    public RobotData(Long id, String xpos, String ypos, String orientation, String depth) {
        super();
        this.id = id;
        this.xpos = xpos;
        this.ypos = ypos;
        this.orientation = orientation;
        this.depth = depth;
    }

    public RobotData(String xpos, String ypos, String orientation, String depth) {
        super();
        this.xpos = xpos;
        this.ypos = ypos;
        this.orientation = orientation;
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
    public String getXpos() {
        return xpos;
    }

    /**
     * @param xpos the xpos to set
     */
    public void setXpos(String xpos) {
        this.xpos = xpos;
    }

    /**
     * @return the ypos
     */
    public String getYpos() {
        return ypos;
    }

    /**
     * @param ypos the ypos to set
     */
    public void setYpos(String ypos) {
        this.ypos = ypos;
    }

    /**
     * @return the orientation
     */
    public String getOrientation() {
        return orientation;
    }

    /**
     * @param orientation the orientation to set
     */
    public void setOrientation(String orientation) {
        this.orientation = orientation;
    }

    /**
     * @return the depth
     */
    public String getDepth() {
        return depth;
    }

    /**
     * @param depth the depth to set
     */
    public void setDepth(String depth) {
        this.depth = depth;
    }

    @Override
    public String toString() {
        return String.format("RobotData [id=%s, xpos=%s, ypos=%s, orientation=%s, depth=%s", id, xpos, ypos, orientation, depth);
    }

}
