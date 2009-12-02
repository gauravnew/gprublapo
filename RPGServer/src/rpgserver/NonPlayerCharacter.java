/*
 * Filename : NonPlayerCharacter.java
 * Description : This class stored data about computer
 * controlled actors.
 */

/** 
  *  TODO	
  *	Update players with random location to use GameMap's getRandomMapPoint()
  *	Add nextDirection enum {VERTICAL(-1), HORIZONTAL(1)}
  */
 
package rpgserver;

/**
 * @author Gaurav
 */
public class NonPlayerCharacter extends Actor {

	enum RELATIVE_DIRECTION{
		VERTICAL(-1), HORIZONTAL(1);
		int data;
		RELATIVE_DIRECTION(int x){
			data = x;
		}
	}
	RELATIVE_DIRECTION nextDirection;
	
	//non player characters that move or have a random location
	public NonPlayerCharacter(int type) {
		if(type<1 || type>4){
			System.out.println("invalid type");
		}else{
			//random position for any type
			Point2D randPt =  Main.cGameLogic.cMapEngine.getRandomMapPoint();
			
			
			this.type = type; //sets Actor's type to the passed in argument type
	        position = new Point2D();
	        position.setPosition(randPt.getX(), randPt.getY());
	        
			if(type == 1){
				//H1N1 - type 1	
		        name = "H1N1";
		        speed = (float) 0.2;
			}else if(type == 2){
				//Professors - type 2
		        name = "Professor";
		        speed = 0;
			}else if(type == 3){
				//Man hole - type 3
		        name = "Manhole";
		        speed = 0;
			}
			
			//default for all types
			moveto = position;
	        status = 0;
	        lastmovetime = 0;
		}
		
	}
	
	//non player characters that are part of the map(classrooms, food buildings...)
	public NonPlayerCharacter(int type, Point2D mapPos){

		if(type<4 || type>24){
			System.out.println("invalid type");
		}else{
			
			this.type = type; //sets Actor's type to the passed in argument type
			
			position = new Point2D();
	        float x = mapPos.getX();
	        float y = mapPos.getY();
	        
	        position.setPosition(x, y);
	        
			if(type == 4){
				//Cranberry Farms - type 4	
		        name = "Cranberry Farms";
			}else if(type == 5){
				//Einstein's Bagels - type 5
		        name = "Einstein's Bagels";
			}else if(type == 6){
				//Jamba Juice - type 6
		        name = "Jamba Juice";
			}else if(type == 7){
				//Man hole - type 7
		        name = "Loose Leafs";
			}else if(type == 8){
				//Loose Leafs - type 8
		        name = "Subway";
			}else if(type == 9){
				//Panda Express - type 9
		        name = "Panda Express";
			}else if(type == 10){
				//Papa John's - type 10
		        name = "Papa John's";
			}else if(type == 11){
				//Starbucks - type 11
		        name = "Starbucks";
			}else if(type == 12){
				//Dunkin Donuts - type 12
		        name = "Dunkin Donuts";
			}else if(type == 13){
				//Store 24 - type 13
		        name = "Store 24";
			}else if(type == 14){
				//BU FitRec - type 14
		        name = "BU FitRec";
			}else if(type == 15){
				//Student Health Services - type 15
		        name = "Student Health Services";
			}else if(type == 16){
				//CS101 - type 16
		        name = "CS101";
			}else if(type == 17){
				//CS103 - type 17
		        name = "CS103";
			}else if(type == 18){
				//CS105 - type 18
		        name = "CS105";
			}else if(type == 19){
				//CS107 - type 19
		        name = "CS107";
			}else if(type == 20){
				//CS108 - type 20
		        name = "CS108";
			}else if(type == 21){
				//CS211 - type 21
		        name = "CS211";
			}else if(type == 22){
				//CS212 - type 22
		        name = "CS212";
			}else if(type == 23){
				//CS411 - type 23
		        name = "CS411";
			}else if(type == 24){
				//CS511 - type 24
		        name = "CS511";
			}
			
			//default for all types
			speed = 0;
			moveto = position;
	        status = 0;
	        lastmovetime = 0;
		}
	}

}
