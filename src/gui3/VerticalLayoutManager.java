/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui3;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;


public class VerticalLayoutManager implements LayoutManager{

  private Dimension size = new Dimension();

    // ��������� ��� ������ �� ������������
    public void addLayoutComponent   (String name, Component comp) {}
    public void removeLayoutComponent(Component comp) {}

    // ����� ����������� ������������ ������� ��� ����������
    public Dimension minimumLayoutSize(Container c) {
        return calculateBestSize(c);
    }
    // ����� ����������� ����������������� ������� ��� ����������
    public Dimension preferredLayoutSize(Container c) {
        return calculateBestSize(c);
    }
    // ����� ������������ ����������� � ����������
    public void layoutContainer(Container container){
        // ������ �����������
        Component list[] = container.getComponents();
        int currentY = 5;
        for (int i = 0; i < list.length; i++) {
            // ����������� ����������������� ������� ����������
            Dimension pref = list[i].getPreferredSize();
            // ���������� ���������� �� ������
            list[i].setBounds(5, currentY, pref.width, pref.height);
            // ��������� ���������� � 5 ��������
            currentY += 5;
            // ������� ������������ ������� ����������
            currentY += pref.height;
        }
    }
    // ����� ���������� ������������ ������� ����������
    private Dimension calculateBestSize(Container c){
        // ���������� ����� ����������
        Component[] list = c.getComponents();
        int maxWidth = 0;
        for (int i = 0; i < list.length; i++) {
            int width = list[i].getWidth();
            // ����� ���������� � ������������ ������
            if ( width > maxWidth ) 
                maxWidth = width;
        }
        // ������ ���������� � ����� � ������ ������ �������
        size.width = maxWidth + 5;
        // ���������� ������ ����������
        int height = 0;
        for (int i = 0; i < list.length; i++) {
            height += 5;
            height += list[i].getHeight();
        }
        size.height = height;
        return size;
  
    }
    
}
