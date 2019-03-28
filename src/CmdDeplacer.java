package vue;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

import modele.Robot;
import modele.CentreControle;
import utilitaires.Vecteur2D;

public class CmdDeplacement extends JPanel {
	private static final int GAP = 50;
	private static final double RATIO_POSITION_LONGUEUR = 0.33;
	private static final double RATIO_POSITION_HAUTEUR = 0.25;
	private static final double RATIO_POSITION_CIBLE_lONGEUR = 0.5;
	
	
	private JLabel position;
	private JTextField positionCibleX;
	private JTextField positionCibleY;
	private JButton boutonDéplacer;
	
	CentreControle centreControle;
	Robot robot;
	
	
	
	public CmdDeplacement(CentreControle centreControle, Robot robot) {
		
		this.setBackground(Color.DARK_GRAY);
		this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.centreControle = centreControle;
		this.robot = robot;
		
		
	}
	
	public void Dessiner() {
		CreerLabelPosition();
		CreerTextPostionCibleX();
		CreerTextPostionCibleY();
		CreerBouttonDéplacerRover();
	}
	
	private void CreerLabelPosition(){
		position = new JLabel();
		double longueurScale = this.getWidth()*RATIO_POSITION_LONGUEUR;
		double hauteurScale = longueurScale * RATIO_POSITION_HAUTEUR;
		updatePosition();
		position.setBounds(this.getWidth()/2-(int)longueurScale/2, GAP, (int)longueurScale, (int)hauteurScale);
	}
	
	private void CreerTextPostionCibleX() {
		positionCibleX = new JTextField();
		int longueurScale = (int) (this.getWidth()*RATIO_POSITION_CIBLE_lONGEUR);
		int hauteurScale = (int) (longueurScale * RATIO_POSITION_HAUTEUR);
		positionCibleX.setText("Pos Cible X");
		positionCibleX.setBounds(this.getWidth()/2-longueurScale/2, position.getHeight()+GAP, longueurScale, hauteurScale);
	}
	
	private void CreerTextPostionCibleY() {
		int longueurScale = (int) (this.getWidth()*RATIO_POSITION_CIBLE_lONGEUR);
		int hauteurScale = (int) (longueurScale * RATIO_POSITION_HAUTEUR);
		positionCibleY = new JTextField();
		positionCibleY.setText("Pos Cible Y");
		positionCibleY.setBounds(this.getWidth()/2-longueurScale/2, positionCibleX.getHeight()+GAP, longueurScale, hauteurScale);
	}
	
	private void CreerBouttonDéplacerRover() {
		boutonDéplacer = new JButton("Déplacer Rover");
		int longueurScale = (int) (this.getWidth()*RATIO_POSITION_CIBLE_lONGEUR);
		int hauteurScale = (int) (longueurScale * RATIO_POSITION_HAUTEUR);
		boutonDéplacer.addActionListener(new EcouteurDéplacerRover());
		boutonDéplacer.setBounds(this.getWidth()/2-longueurScale/2, positionCibleY.getHeight()+GAP, longueurScale, hauteurScale);
	}
	
	class EcouteurDéplacerRover implements ActionListener{
		public void actionPerformed(ActionEvent event) {
			centreControle.commandeDeplacer(new Vecteur2D(Double.parseDouble(positionCibleX.getText()),Double.parseDouble(positionCibleY.getText())));
		}
	}
	
	private void updatePosition() {
		position.setText("Pos courante X:" + robot.getPosition().getX() + "/n" + "Pos courante Y:"  + robot.getPosition().getX());
	}
	
	public void paintComponent(Graphics g) {
		updatePosition();
	}
	
}
