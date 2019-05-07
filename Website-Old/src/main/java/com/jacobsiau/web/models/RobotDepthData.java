package com.jacobsiau.web.models;

/**
 * RobotDepthData is a POJO to represent entries in the ROBOT_DEPTH_DATA table.
 */
public class RobotDepthData {

    private Long id;
    private Double depth;

    public RobotDepthData() {
        super();
    }

    public RobotDepthData(Long id, Double depth) {
        super();
        this.id = id;
        this.depth = depth;
    }

    public RobotDepthData(Double depth) {
        super();
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
