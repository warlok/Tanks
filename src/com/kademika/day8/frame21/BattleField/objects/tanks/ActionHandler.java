package com.kademika.day8.frame21.BattleField.objects.tanks;

import com.kademika.day8.frame21.interfaces.Tank;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by dean on 5/26/15.
 */
public class ActionHandler extends AbstractAction {

    public ActionHandler(T34 tank, Action action) {
        tank.setAction(action);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
