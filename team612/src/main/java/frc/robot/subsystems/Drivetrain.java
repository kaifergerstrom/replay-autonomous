/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

public class Drivetrain extends SubsystemBase {
  

  private WPI_TalonSRX talon_left_drive = new WPI_TalonSRX(Constants.talon_left_drive_port);
	private WPI_TalonSRX talon_right_drive = new WPI_TalonSRX(Constants.talon_right_drive_port);


  public Drivetrain() {
    setBrakeMode(NeutralMode.Brake);
    enableVoltageScaler(11);
  }


  public void arcadeDrive(double throttle, double turn) {

    // Limit throttle and turn if over limit
    throttle = throttle > 1 ? 1 : throttle < -1 ? -1 : throttle;
    turn = turn > 1 ? 1 : turn < -1 ? -1 : turn;

    // Output variables for left and right sides
    double leftMotorOutput;
    double rightMotorOutput;

    double maxInput = Math.copySign(Math.max(Math.abs(throttle), Math.abs(turn)), throttle);
    
    if (throttle >= 0.0) {  // Determine direction robot is moving in

      leftMotorOutput = turn >= 0.0 ? maxInput : throttle + turn;
      rightMotorOutput = turn >= 0.0 ? throttle - turn : maxInput;

    } else {

      leftMotorOutput = turn >= 0.0 ? throttle + turn : maxInput;
      rightMotorOutput = turn >= 0.0 ? maxInput : throttle - turn;
      
    }

    // Limit output values if over limit
    leftMotorOutput = leftMotorOutput > 1 ? 1 : leftMotorOutput < -1 ? -1 : leftMotorOutput;
    rightMotorOutput = rightMotorOutput > 1 ? 1 : rightMotorOutput < -1 ? -1 : rightMotorOutput;

    talon_left_drive.set(leftMotorOutput);
    talon_right_drive.set(rightMotorOutput);

  }


  private void enableVoltageScaler(double voltage) {  // Redefine upper limit of PercentOutput 100% = voltage
    talon_left_drive.configVoltageCompSaturation(voltage);
    talon_left_drive.enableVoltageCompensation(true);
    talon_right_drive.configVoltageCompSaturation(voltage);
    talon_right_drive.enableVoltageCompensation(true);
  }


  private void setBrakeMode(NeutralMode neutralMode) {
    talon_left_drive.setNeutralMode(neutralMode);
    talon_right_drive.setNeutralMode(neutralMode);
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
  
}
