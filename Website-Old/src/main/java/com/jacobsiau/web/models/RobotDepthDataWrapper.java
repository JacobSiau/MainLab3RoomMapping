package com.jacobsiau.web.models;

import java.util.List;

/**
 * RobotDepthDataWrapper is a wrapper class for a list of {@link RobotDepthData} objects so we can send/recieve these as a list via the controller methods.
 */
public class RobotDepthDataWrapper {

    private List<RobotDepthData> depthData;

    public RobotDepthDataWrapper() {
        super();
    }

    public RobotDepthDataWrapper(List<RobotDepthData> depthData) {
        super();
        this.depthData = depthData;
    }

   /**
    * @return the depthData
    */
   public List<RobotDepthData> getDepthData() {
       return depthData;
   }

   /**
    * @param depthData the depthData to set
    */
   public void setDepthData(List<RobotDepthData> depthData) {
       this.depthData = depthData;
   }
}
