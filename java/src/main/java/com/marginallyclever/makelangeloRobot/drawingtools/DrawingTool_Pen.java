package com.marginallyclever.makelangeloRobot.drawingtools;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.marginallyclever.makelangelo.FloatField;
import com.marginallyclever.makelangelo.Translator;
import com.marginallyclever.makelangeloRobot.MakelangeloRobot;
import com.marginallyclever.makelangeloRobot.settings.SelectColor;


public class DrawingTool_Pen extends DrawingTool implements ActionListener {
	protected JDialog dialog;
	protected JPanel panel;
	
	protected FloatField penDiameter;
	protected FloatField penFeedRate;
	protected FloatField penUp;
	protected FloatField penDown;
	protected FloatField penZRate;

	protected JButton buttonTestUp;
	protected JButton buttonTestDown;
	protected JButton buttonSave;
	protected JButton buttonCancel;
	
	protected SelectColor selectColor;

	
	public DrawingTool_Pen(MakelangeloRobot robot) {
		super(robot);

		diameter = 1.5f;
		zRate = 50;
		zOn = 90;
		zOff = 50;
		toolNumber = 0;
		feedRateXY = 3500;
		name = "Pen";
		toolColor = Color.BLACK;
	}

	public DrawingTool_Pen(String name2, int tool_id, MakelangeloRobot robot) {
		super(robot);

		diameter = 0.8f;
		zRate = 50;
		zOn = 90;
		zOff = 50;
		toolNumber = tool_id;
		feedRateXY = 6500;
		name = name2;
	}

	public JPanel getPanel() {
		panel = new JPanel();
		panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
	    panel.setBorder(BorderFactory.createEmptyBorder(16,16,16,16));

	    JPanel p = new JPanel(new GridBagLayout());
	    panel.add(p);
	    
		penDiameter = new FloatField(getDiameter());
		penFeedRate = new FloatField(feedRateXY);
		penUp = new FloatField(zOff);
		penDown = new FloatField(zOn);
		penZRate = new FloatField(zRate);
		buttonTestUp = new JButton(Translator.get("penToolTest"));
		buttonTestDown = new JButton(Translator.get("penToolTest"));

	    Dimension s = buttonTestUp.getPreferredSize();
	    s.width = 80;
	    buttonTestUp.setPreferredSize(s);
	    buttonTestDown.setPreferredSize(s);

		GridBagConstraints c = new GridBagConstraints();
		GridBagConstraints d = new GridBagConstraints();

		c.ipadx=5;
	    c.ipady=0;

		c.anchor = GridBagConstraints.EAST;
		c.fill = GridBagConstraints.HORIZONTAL;
		d.anchor = GridBagConstraints.WEST;
		d.fill = GridBagConstraints.HORIZONTAL;
		d.weightx = 50;
		int y = 0;

		c.gridx = 0;
		c.gridy = y;
		p.add(new JLabel(Translator.get("penToolDiameter")), c);
		d.gridx = 1;
		d.gridy = y;
		p.add(penDiameter, d);
		++y;

		c.gridx = 0;
		c.gridy = y;
		p.add(new JLabel(Translator.get("penToolMaxFeedRate")), c);
		d.gridx = 1;
		d.gridy = y;
		p.add(penFeedRate, d);
		++y;

		c.gridx = 0;
		c.gridy = y;
		p.add(new JLabel(Translator.get("penToolUp")), c);
		d.gridx = 1;
		d.gridy = y;
		p.add(penUp, d);
		d.gridx = 2;
		d.gridy = y;
		p.add(buttonTestUp, d);
		++y;

		c.gridx = 0;
		c.gridy = y;
		p.add(new JLabel(Translator.get("penToolDown")), c);
		d.gridx = 1;
		d.gridy = y;
		p.add(penDown, d);
		d.gridx = 2;
		d.gridy = y;
		p.add(buttonTestDown, d);
		++y;

		c.gridx = 0;
		c.gridy = y;
		p.add(new JLabel(Translator.get("penToolLiftSpeed")), c);
		d.gridx = 1;
		d.gridy = y;
		p.add(penZRate, d);
		++y;

		c.gridwidth = 2;
		c.insets = new Insets(0, 5, 5, 5);
		c.anchor = GridBagConstraints.WEST;
		
		buttonTestUp.addActionListener(this);
		buttonTestDown.addActionListener(this);
		
		JPanel colorPanel = new JPanel(new GridBagLayout());
		GridBagConstraints cm = new GridBagConstraints();
		cm.gridx=0;
		cm.gridy=0;
		cm.fill=GridBagConstraints.HORIZONTAL;
		selectColor = new SelectColor(panel,"pen color",robot.getSettings().getPenColor());
		colorPanel.add(selectColor,cm);
		c.gridy++;
		panel.add(colorPanel,c);
		
		return panel;
	}
	
	
	public void actionPerformed(ActionEvent event) {
		Object subject = event.getSource();

		if (subject == buttonTestUp  ) robot.testPenAngle(((Number)penUp.getValue()).floatValue());
		if (subject == buttonTestDown) robot.testPenAngle(((Number)penDown.getValue()).floatValue());
	}
	
	
	@Override
	public void save() {
		setDiameter(((Number)penDiameter.getValue()).floatValue());
		feedRateXY = ((Number)penFeedRate.getValue()).floatValue();
		zRate = ((Number)penZRate.getValue()).floatValue();
		zOff = ((Number)penUp.getValue()).floatValue();
		zOn = ((Number)penDown.getValue()).floatValue();
		robot.getSettings().setPenColor(selectColor.getColor());
	}
}
