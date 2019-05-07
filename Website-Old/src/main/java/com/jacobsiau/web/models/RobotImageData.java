package com.jacobsiau.web.models;

/**
 * RobotImageData is a POJO to represent entries in the ROBOT_IMAGE_DATA table.
 */
public class RobotImageData {

    private Long id;
    private Double depth;


    public RobotImageData() {
        super();
    }

    public RobotImageData(Long id, Double depth) {
        super();
        this.id = id;
        this.depth = depth;
    }

    public RobotImageData(Double depth) {
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
