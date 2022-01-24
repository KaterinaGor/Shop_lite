/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui3;

import java.awt.Dimension;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class JManagerPanel extends JPanel{

  public JManagerPanel() {
    this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    this.add(getPanelCaption());
    this.add(getPanelPrice());
    this.add(getPanelQuantity());
  }
  
  public JPanel getPanelCaption() {
    JPanel panelCaption = new JPanel();
    panelCaption.setPreferredSize(new Dimension(480,290));
    panelCaption.setMaximumSize(panelCaption.getPreferredSize());
    
    JLabel labelCaption = new JLabel("Название",SwingConstants.RIGHT);
    labelCaption.setPreferredSize(new Dimension(120,27));
    labelCaption.setMaximumSize(labelCaption.getPreferredSize());
    panelCaption.add(labelCaption);
    
    JTextField jTextField = new JTextField();
    jTextField.setPreferredSize(new Dimension(300,27));
    jTextField.setMaximumSize(jTextField.getPreferredSize());
    panelCaption.add(jTextField);
    return panelCaption;
  }
  public JPanel getPanelPrice() {
    JPanel panelPublishedYear = new JPanel();
    panelPublishedYear.setPreferredSize(new Dimension(480,290));
    panelPublishedYear.setMaximumSize(panelPublishedYear.getPreferredSize());
    
    JLabel labelCaption = new JLabel("Стоимость",SwingConstants.RIGHT);
    labelCaption.setPreferredSize(new Dimension(120,27));
    labelCaption.setMaximumSize(labelCaption.getPreferredSize());
    panelPublishedYear.add(labelCaption);
    
    JTextField jTextField = new JTextField();
    jTextField.setPreferredSize(new Dimension(300,27));
    jTextField.setMaximumSize(jTextField.getPreferredSize());
    panelPublishedYear.add(jTextField);
    return panelPublishedYear;
  }
  public JPanel getPanelQuantity() {
    JPanel panelQuantity = new JPanel();
    panelQuantity.setPreferredSize(new Dimension(480,290));
    panelQuantity.setMaximumSize(panelQuantity.getPreferredSize());
    
    JLabel labelCaption = new JLabel("Количество",SwingConstants.RIGHT);
    labelCaption.setPreferredSize(new Dimension(120,27));
    labelCaption.setMaximumSize(labelCaption.getPreferredSize());
    panelQuantity.add(labelCaption);
    
    JTextField jTextField = new JTextField();
    jTextField.setPreferredSize(new Dimension(300,27));
    jTextField.setMaximumSize(jTextField.getPreferredSize());
    panelQuantity.add(jTextField);
    return panelQuantity;
  }
    
}
