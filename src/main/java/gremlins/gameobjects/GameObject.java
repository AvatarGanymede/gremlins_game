package gremlins.gameobjects;

import gremlins.Game;
import processing.core.PImage;
import processing.core.PVector;

public abstract class GameObject {
    protected PVector m_Position;
    protected PImage m_Image;
    public GameObject(){
        m_Position = new PVector(0, 0);
    }
    public GameObject(int x, int y, PImage image){
        SetPosition(x, y);
        SetImage(image);
    }
    public void SetPosition(int x, int y){
        m_Position = new PVector(x, y);
    }
    public void SetImage(PImage image) {
        m_Image = image;
    }
    public void Update(Game game){
        if(game != null && m_Image != null){
            game.image(m_Image, m_Position.x, m_Position.y);
        }
    }
    public void Move(GameConst d){
        
    }
}
