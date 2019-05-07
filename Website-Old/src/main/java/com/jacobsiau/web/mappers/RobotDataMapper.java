package com.jacobsiau.web.mappers;

import org.apache.ibatis.annotations.*;
import com.jacobsiau.web.models.RobotData;
import com.jacobsiau.web.models.RobotDepthData;
import com.jacobsiau.web.models.RobotImageData;
import com.jacobsiau.web.models.RobotImageDataEntry;

import java.util.List;

/**
 * RobotDataMapper is an interface used by MyBatis to execute SQL commands for this application's in-memory H2 database.
 */
@Mapper
public interface RobotDataMapper {

    @Select("SELECT * FROM ROBOT_DATA")
    public List<RobotData> getAllRobotData();

    @Select("SELECT * FROM ROBOT_DEPTH_DATA")
    public List<RobotDepthData> getAllRobotDepthData();

    @Delete("DELETE FROM ROBOT_DEPTH_DATA")
    public void deleteAllRobotDepthData();

    @Update("ALTER TABLE ROBOT_DEPTH_DATA ALTER COLUMN ID RESTART WITH 1")
    public void updateRobotDepthDataRestartNumbering();

    @Insert("INSERT INTO ROBOT_DEPTH_DATA (depth) VALUES (#{depth})")
    @Options(useGeneratedKeys = true, keyProperty = "robotdepthdata.id", keyColumn = "id")
    public void insertRobotDepthData(@Param("depth") Double depth);

    @Select("SELECT * FROM ROBOT_IMAGE_DATA")
    public List<RobotImageDataEntry> getAllRobotImageData();

    @Insert("INSERT INTO ROBOT_IMAGE_DATA (xpos, ypos, depth) VALUES (#{xpos}, #{ypos}, #{depth})")
    @Options(useGeneratedKeys = true, keyProperty = "robotimagedata.id", keyColumn = "id")
    public void insertRobotImageData(@Param("xpos") Double xpos, @Param("ypos") Double ypos, @Param("depth") Double depth);

    @Update("ALTER TABLE ROBOT_IMAGE_DATA ALTER COLUMN ID RESTART WITH 1")
    public void updateRobotImageDataRestartNumbering();

    @Delete("DELETE FROM ROBOT_IMAGE_DATA")
    public void deleteAllRobotImageData();

}
