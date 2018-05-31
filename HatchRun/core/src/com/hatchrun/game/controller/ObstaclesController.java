package com.hatchrun.game.controller;

import com.badlogic.gdx.Gdx;
import com.hatchrun.game.model.GameModel;
import com.hatchrun.game.model.entities.CoinModel;
import com.hatchrun.game.model.entities.EntityModel;
import com.hatchrun.game.model.entities.ObstacleModel;

public class ObstaclesController {

    private long lastTimeRegisteredObstacles = 0;

    public void update(){
        if(System.currentTimeMillis() - lastTimeRegisteredObstacles >= GameModel.getInstance().OBSTACLE_TIME){
            generateObstacle();
            lastTimeRegisteredObstacles = System.currentTimeMillis();
        }
    }


    /**
     * Generates new obstacle.
     */
    private ObstacleModel generateObstacleAux() {
        ObstacleModel tempObs = null;


        switch (GameController.rand.nextInt(3)){
            case 0:
                tempObs = new ObstacleModel(EntityModel.ElementLane.LEFT,GameController.leftX, Gdx.graphics.getHeight(), generateColour());
                break;
            case 1:
                tempObs = new ObstacleModel(EntityModel.ElementLane.MIDDLE,GameController.centerX,Gdx.graphics.getHeight(), generateColour());
                break;
            case 2:
                tempObs = new ObstacleModel(EntityModel.ElementLane.RIGHT,GameController.rightX,Gdx.graphics.getHeight(), generateColour());
                break;
            default:
                break;
        }
        return tempObs;
    }



    /**
     * Generates new obstacle.
     */
    private void generateObstacle() {
        ObstacleModel temp = generateObstacleAux();


       /* while(checkCollisionOtherObjetcs(temp)){
            temp = generateObstacleAux();
        }*/

       if(!checkCollisionOtherObjetcs(temp))
        GameModel.getInstance().getObstacles().add(temp);
    }


    private boolean checkCollisionOtherObjetcs(ObstacleModel tempObs){
        ObstacleModel temp2;


        for(CoinModel coin: GameModel.getInstance().getCoins()){
            if (GameController.checkOverlap(tempObs, new CoinModel(coin.getLane(), coin.getX(), coin.getY() + coin.getHeight() / 2))
                    || GameController.checkOverlap(tempObs, new CoinModel(coin.getLane(), coin.getX(), coin.getY() - coin.getHeight() / 2))
                    || GameController.checkOverlap(tempObs, new CoinModel(coin.getLane(), coin.getX(), coin.getY()))) {
                return true;
            }
        }

        if(GameModel.getInstance().getObstacles().size()!=0) {
            temp2 = GameModel.getInstance().getObstacles().get(GameModel.getInstance().getObstacles().size() - 1);

            if (GameController.checkOverlap(tempObs, new CoinModel(temp2.getLane(), temp2.getX(), temp2.getY() + 2*temp2.getHeight()))
                    || GameController.checkOverlap(tempObs, new CoinModel(temp2.getLane(), temp2.getX(), temp2.getY() - 2*temp2.getHeight()))
                    || GameController.checkOverlap(tempObs, new CoinModel(temp2.getLane(), temp2.getX(), temp2.getY()))) {
                return true;
            }
        }
        return false;
    }



    /**
     * Generates an obstacle colour.
     * @return generated colour
     */
    private ObstacleModel.Colour generateColour(){
        switch (GameController.rand.nextInt(3)){
            case 0:
                return ObstacleModel.Colour.PINK;
            case 1:
                return ObstacleModel.Colour.BLUE;
            case 2:
                return ObstacleModel.Colour.YELLOW;
            default:
                return ObstacleModel.Colour.PINK;
        }
    }
}