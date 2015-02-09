/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.lhsalliance.aquatic.entities;

import com.jme3.collision.CollisionResults;
import com.jme3.math.FastMath;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import com.lhsalliance.aquatic.core.Main;

/**
 *
 * @author Will
 */
public class KillAI implements AI
{
    private float radius;
    
    public KillAI(float searchRadius)
    {
        this.radius = searchRadius;
    }

    @Override
    public void onUpdate(float tpf, Animal animal) {
        Node node = animal.model.node;
        Node player = Main.game.player.model.node;
        
        Vector3f posAnimal = node.getLocalTranslation();
        Vector3f posPlayer = player.getLocalTranslation();
        
        float difX = posAnimal.x - posPlayer.x;
        float difZ = posAnimal.z - posPlayer.z;
        
        double dist = Math.sqrt(Math.pow(difX, 2) + Math.pow(difZ, 2));
        
        float angle = (float)Math.atan(difX/difZ) - (180*FastMath.DEG_TO_RAD);
        
        if (difZ <= 0)
        {
            angle = (float)Math.atan(difZ/difX);
        }
        
        if (difZ == 0 && difX < 0)
        {
            angle = 0;
        }
        
        if (dist < 2)
        {
            if (Main.game.ticks % 50 == 0)
            {
                Main.game.player.damage(8, animal);
                System.out.println("OM NOM");
            }
        }
        else if (dist < this.radius)
        {
            node.setLocalRotation(new Quaternion().fromAngleAxis(angle, new Vector3f(0,1,0)));
            Vector3f forward = node.getLocalRotation().getRotationColumn(2);
            node.move(forward.divide(4));
        }
    }
    
}