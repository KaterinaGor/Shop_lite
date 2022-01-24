/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui3;

import entity.Client;
import java.awt.Dimension;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class JClientPanel extends JPanel{

  public JClientPanel() {
    this.setPreferredSize(new Dimension(480,290));;
    this.setMaximumSize(this.getPreferredSize());
    JLabel jLabelClients = new JLabel("CLIENTS");
    this.add(jLabelClients);
    JComboBox<Client> jComboBoxClient = new JComboBox<>();
    jComboBoxClient.setPreferredSize(new Dimension(300,27));
    jComboBoxClient.setMaximumSize(jComboBoxClient.getPreferredSize());
    this.add(jComboBoxClient);
  }
    
}
