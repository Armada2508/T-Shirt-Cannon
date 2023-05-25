package frc.robot.lib.drive;

import java.util.function.Supplier;

import edu.wpi.first.math.Vector;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.math.kinematics.DifferentialDriveWheelSpeeds;
import edu.wpi.first.math.numbers.N2;
import frc.robot.lib.util.Util;

public class FieldOrientedDrive {
    private Supplier<Vector<N2>> mVelocity;
    private Supplier<Rotation2d> mHeading;
    private double mMaxVelocity;
    private PIDController mTurnController;
    private DifferentialDriveKinematics mKinematics;

    
    /**
     * Creates a new FieldOrientedDrive Object
     * @param maxVelocity The maximum velocity of the robot
     * @param turnController The PID controller used for turning
     * @param trackWidth The width of the drivetrain
     */
    public FieldOrientedDrive(double maxVelocity, PIDController turnController, double trackWidth) {
        mMaxVelocity = maxVelocity;
        mTurnController = turnController;
        mKinematics = new DifferentialDriveKinematics(trackWidth);
    }

    /**
     * Creates a new FieldOrientedDrive Object
     * @param velocity The global velocity of the robot(+X is away from alliance wall, +Y is left facing opposing alliance)
     * @param heading The heading of the robot in radians
     * @param maxVelocity The maximum velocity of the robot
     * @param turnController The PID controller used for turning
     * @param trackWidth The width of the drivetrain
     */
    public FieldOrientedDrive(Supplier<Vector<N2>> velocity, Supplier<Rotation2d> heading, double maxVelocity, PIDController turnController, double trackWidth) {
        mVelocity = velocity;
        mHeading = heading;
        mMaxVelocity = maxVelocity;
        mTurnController = turnController;
        mKinematics = new DifferentialDriveKinematics(trackWidth);
    }

    /**
     * @return The wheel speeds for the robot
     */
    public DifferentialDriveWheelSpeeds calculate() {
        if(mVelocity == null || mHeading == null) {
            return new DifferentialDriveWheelSpeeds(0.0, 0.0);
        }
        return calculate(mVelocity.get(), mHeading.get());
    }
    
    /**
     * Resets the controller
     */
    public void reset() {
        mTurnController.reset();
    }

    

    /**
     * @param velocity The global velocity of the robot(+X is away from alliance wall, +Y is left facing opposing alliance)
     * @param heading The heading of the robot in radians
     * @return The wheel speeds for the robot
     */
    public DifferentialDriveWheelSpeeds calculate(Vector<N2> velocity, Rotation2d heading) {
        double desiredGlobalHeading = Math.atan2(velocity.get(0, 0), velocity.get(0, 1));
        double localHeading = Util.boundedAngle(heading.getRadians() - desiredGlobalHeading);
        double omega = mTurnController.calculate(localHeading);
        ChassisSpeeds localVelocity = ChassisSpeeds.fromFieldRelativeSpeeds(velocity.get(0, 0), velocity.get(0, 1), omega, heading);
        DifferentialDriveWheelSpeeds speeds = mKinematics.toWheelSpeeds(localVelocity);
        double lVelocity = speeds.leftMetersPerSecond;
        double rVelocity = speeds.rightMetersPerSecond;
        if(Math.abs(lVelocity) > mMaxVelocity || Math.abs(rVelocity) > mMaxVelocity) {
            if(Math.abs(lVelocity) > Math.abs(rVelocity)) {
                lVelocity /= Math.abs(lVelocity / mMaxVelocity);
                rVelocity /= Math.abs(lVelocity / mMaxVelocity);
            } else {
                lVelocity /= Math.abs(rVelocity / mMaxVelocity);
                rVelocity /= Math.abs(rVelocity / mMaxVelocity);
            }
        }
        return new DifferentialDriveWheelSpeeds(lVelocity, rVelocity);
    }
}