package gremlins.gameobjects;

import processing.core.PImage;

public class Player extends GameObject{
    private PImage m_Spell;
    private float m_SkillCoolDown;
    private int m_Velocity;
    public Player(int x, int y, PImage image, float skillCoolDown, int velocity, PImage spell){
        super(x, y, image);
        SetSpell(spell);
        SetSkillCoolDown(skillCoolDown);
        SetVelocity(velocity);
    }
    public void SetSpell(PImage spell){
        m_Spell = spell;
    }
    public void SetSkillCoolDown(float coolDown){
        m_SkillCoolDown = coolDown;
    }
    public void SetVelocity(int velocity){
        m_Velocity = velocity;
    }
}
