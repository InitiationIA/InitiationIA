package Action;

import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;
import java.io.IOException;
import java.net.URI;
import java.nio.file.FileSystem;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchEvent.Modifier;


import lejos.hardware.motor.JavaMotorRegulator;
import lejos.hardware.motor.Motor;
import lejos.hardware.motor.MotorRegulator;
import lejos.robotics.RegulatedMotor;
import lejos.robotics.RegulatedMotorListener;
import lejos.robotics.chassis.Chassis;
import lejos.robotics.chassis.Wheel;
import lejos.robotics.localization.PoseProvider;
import lejos.robotics.navigation.*;
import lejos.hardware.motor.EV3LargeRegulatedMotor;
import lejos.hardware.port.Port;



public class Motors implements MotorRegulator{
	
		/*
		 * ----------ATTRIBUTES----------
		 */
		
		/*
		 * Declares a left motor, a right motor, and a constant speed.
		 */
		private final EV3LargeRegulatedMotor mLeftMotor;
		private final EV3LargeRegulatedMotor mRightMotor;
		private float speed = 360;
		private int acceleration = 6000;
		

		
		
		private final static int SPEED = 50;
		
		
		/*
		 * ----------CONSTRUCTORS----------
		 */
		
		/*
		 * Initializes the left and right motors, and affects them the same speed
		 * in order to have a synchronous movement
		 */
		public Motors(Port leftPort, Port rightPort) {
			
			mLeftMotor = new EV3LargeRegulatedMotor(leftPort);
			mRightMotor = new EV3LargeRegulatedMotor(rightPort);
			
			mLeftMotor.setSpeed(SPEED);
			mLeftMotor.setSpeed(SPEED);
		}
		
		
		/*
		 * ----------METHODS----------
		 */
	    public void newMove(float speed, int acceleration, int limit, boolean hold, boolean waitComplete) {
	    	speed = this.speed;
	    	acceleration = this.acceleration;
	   	
	    }
	    
	    
	    public void goForward() {
	    	
	    }
	    
	    
		
		/*
		 * goes forward with a max distance
		 */
		public void forward(int distance) {
			
			mLeftMotor.forward();
			mRightMotor.forward();
		}
		
		/*
		 * goes backward with a max distace
		 */
		public void backward(int distance) {
			
			mLeftMotor.backward();
			mRightMotor.backward();
			
		}
		
		
		public void rotateRight(int angle, int speed) {
			
			mRightMotor.forward();
			mLeftMotor.backward();
			
		}
		
		public void rotateLeft(int angle, int speed) {
			
			mLeftMotor.forward();
			mRightMotor.backward();
			
		}


		@Override
		public void setControlParamaters(int typ, float moveP, float moveI, float moveD, float holdP, float holdI,
				float holdD, int offset) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public int getTachoCount() {
			// TODO Auto-generated method stub
			return 0;
		}


		@Override
		public void resetTachoCount() {
			// TODO Auto-generated method stub
			
		}


		@Override
		public boolean isMoving() {
			// TODO Auto-generated method stub
			return false;
		}


		@Override
		public float getCurrentVelocity() {
			// TODO Auto-generated method stub
			return 0;
		}


		@Override
		public void setStallThreshold(int error, int time) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public float getPosition() {
			// TODO Auto-generated method stub
			return 0;
		}


		@Override
		public void adjustSpeed(float newSpeed) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void adjustAcceleration(int newAcc) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void waitComplete() {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void addListener(RegulatedMotor motor, RegulatedMotorListener listener) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public RegulatedMotorListener removeListener() {
			// TODO Auto-generated method stub
			return null;
		}


		@Override
		public int getLimitAngle() {
			// TODO Auto-generated method stub
			return 0;
		}


		@Override
		public boolean isStalled() {
			// TODO Auto-generated method stub
			return false;
		}


		@Override
		public void startSynchronization() {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void endSynchronization(boolean b) {
			// TODO Auto-generated method stub
			
		}


		@Override
		public void synchronizeWith(MotorRegulator[] rl) {
			// TODO Auto-generated method stub
			
		}
		
		
		
		
		/*
		
		
		MoveController moveController = new MoveController() {
			
			
			@Override
			public Move getMovement() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void addMoveListener(MoveListener listener) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void travel(double distance, boolean immediateReturn) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void travel(double distance) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void stop() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setLinearSpeed(double speed) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setLinearAcceleration(double acceleration) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isMoving() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public double getMaxLinearSpeed() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public double getLinearSpeed() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public double getLinearAcceleration() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public void forward() {
				 mLeftMotor.forward();
			     mRightMotor.forward();
				
			}
			
			@Override
			public void backward() {
				mLeftMotor.backward();
			    mRightMotor.backward();				
			}
		};
		
		Path path = new Path() {
			
			@Override
			public URI toUri() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Path toRealPath(LinkOption... options) throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Path toAbsolutePath() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Path subpath(int beginIndex, int endIndex) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean startsWith(Path other) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Path resolve(Path other) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Path relativize(Path other) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public WatchKey register(WatchService watcher, Kind<?>[] events, Modifier... modifiers) throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Path normalize() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean isAbsolute() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public Path getRoot() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Path getParent() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getNameCount() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Path getName(int index) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public FileSystem getFileSystem() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Path getFileName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean endsWith(Path other) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public int compareTo(Path other) {
				// TODO Auto-generated method stub
				return 0;
			}
		};
		
		PoseProvider poseProvider = new PoseProvider() {
			
			@Override
			public void setPose(Pose aPose) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public Pose getPose() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		
		Waypoint waypoint = new Waypoint(4, 7);
		
		Navigator nav1 = new Navigator(null);
		Navigator nav2 = new Navigator(null, null);
		*/
		
	
	
	
}
