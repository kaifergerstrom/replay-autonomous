/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autonomous;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj2.command.CommandBase;

public class ReplayRobot extends CommandBase {
  

  private String DIRECTORY = "/home/lvuser/";  // Directory to ROBORIO
  private String FILENAME;  // Output file name for movement

  private boolean END_REPLAY = false;  // Boolean to end the function if neccesary

  private JSONArray frames;  // Empty parser object to fetch information

  
  public ReplayRobot(String FILENAME) {
    frames = parse_json(FILENAME);
  }


  @Override
  public void initialize() {
  }


  @Override
  public void execute() {

  }


  @Override
  public void end(boolean interrupted) {
  }


  @Override
  public boolean isFinished() {
    return END_REPLAY;
  }


  /* ----- Custom Functions Below ----- */

  private JSONArray parse_json(String file_name) {

    JSONParser jsonParser = new JSONParser();

    try (FileReader reader = new FileReader(file_name)) {

      Object obj = jsonParser.parse(reader);  // Parsed object from JSON
      
      return (JSONArray) obj;

    } catch (FileNotFoundException e) {
      System.out.println("File does not exist!");
      END_REPLAY = true;
      return null;
    } catch (IOException e) {
      System.out.println("Unable to read file!");
      END_REPLAY = true;
      return null;
    } catch (ParseException e) {
      System.out.println("Invalid JSON format!");
      END_REPLAY = true;
      return null;
    }

  }


  // Get factor to multiply motor values to factor battery depletion
  private double getVoltageCompensation(double current_voltage, double replay_voltage) {
    return current_voltage / replay_voltage;
  }


}
