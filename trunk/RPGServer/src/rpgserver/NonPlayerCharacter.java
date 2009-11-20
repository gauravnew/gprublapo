/*
 * Filename : NonPlayerCharacter.java
 * Description : This class stored data about computer
 * controlled actors.
 */

package rpgserver;

/**
 * @author Gaurav
 */
public class NonPlayerCharacter extends Actor {

	//non player characters that move or have a random location
	public NonPlayerCharacter(int type) {
		if(type<1 && type>4){
			System.out.println("invalid type");
		}else{
			//random position for any type
			float randX = (float) Math.random();
			float randY = (float) Math.random();
			
	        position = new Point2D();
	        position.setPosition(randX, randY);
	        
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
			moveto = new Point2D();
	        status = 0;
	        lastmovetime = 0;
		}
		
	}
	
	//non player characters that are part of the map(classrooms, food buildings...)
	public NonPlayerCharacter(int type, Point2D mapPos){

		if(type<4 && type>24){
			System.out.println("invalid type");
		}else{
			position = new Point2D();
	        float x = mapPos.getX();
	        float y = mapPos.getY();
	        
	        position.setPosition(x, y);
	        
			if(type == 4){
				//H1N1 - type 1	
		        name = "Cranberry Farms";
			}else if(type == 5){
				//Professors - type 2
		        name = "Einstein's Bagels";
			}else if(type == 6){
				//Man hole - type 2
		        name = "Jamba Juice";
			}else if(type == 7){
				//Man hole - type 2
		        name = "Loose Leafs";
			}else if(type == 8){
				//Man hole - type 2
		        name = "Subway";
			}else if(type == 9){
				//Man hole - type 2
		        name = "Panda Express";
			}else if(type == 10){
				//Man hole - type 2
		        name = "Papa John's";
			}else if(type == 11){
				//Man hole - type 2
		        name = "Starbucks";
			}else if(type == 12){
				//Man hole - type 2
		        name = "Dunkin Donuts";
			}else if(type == 13){
				//Man hole - type 2
		        name = "Store 24";
			}else if(type == 14){
				//Man hole - type 2
		        name = "BU FitRec";
			}else if(type == 15){
				//Man hole - type 2
		        name = "Student Health Services";
			}else if(type == 16){
				//Man hole - type 2
		        name = "CS101";
			}else if(type == 17){
				//Man hole - type 2
		        name = "CS103";
			}else if(type == 18){
				//Man hole - type 2
		        name = "CS105";
			}else if(type == 19){
				//Man hole - type 2
		        name = "CS107";
			}else if(type == 20){
				//Man hole - type 2
		        name = "CS108";
			}else if(type == 21){
				//Man hole - type 2
		        name = "CS211";
			}else if(type == 22){
				//Man hole - type 2
		        name = "CS212";
			}else if(type == 23){
				//Man hole - type 2
		        name = "CS411";
			}else if(type == 24){
				//Man hole - type 2
		        name = "CS511";
			}
			
			//default for all types
			speed = 0;
			moveto = new Point2D();
	        status = 0;
	        lastmovetime = 0;
		}
	}

}
