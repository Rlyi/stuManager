package Depart;

import java.sql.ResultSet;
import java.awt.*;

import javax.swing.*;

import db.dbConn;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * 
 * Title: Ժϵ�޸� 
 * Description: Ժϵ��Ϣ�޸�ģ��
 * 
 * @author л�ϻ�
 */

public class DepartChange extends JFrame {
	JPanel contentPane;
	JLabel jLabel1 = new JLabel();
	JLabel jLabel3 = new JLabel();
	JButton jButton1 = new JButton();
	JButton jButton2 = new JButton();
	JOptionPane jOptionPane1 = new JOptionPane();
	JComboBox jComboBox1 = new JComboBox();
	dbConn conn = new dbConn();
	JLabel jLabel4 = new JLabel();
	JComboBox jComboBox2 = new JComboBox();
	String find;
	String zhuanye, xueyuan;

	public DepartChange(String find) {
		this.find = find;
		try {
			jbInit();
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		contentPane = (JPanel) getContentPane();
		contentPane.setLayout(null);
		setSize(new Dimension(465, 280));
		setTitle("Ժϵ�޸�");
		jLabel1.setFont(new java.awt.Font("Dialog", Font.BOLD, 20));
		jLabel1.setText("Ժ  ϵ  ��  Ϣ  ��  ��");
		jLabel1.setBounds(new Rectangle(136, 20, 212, 25));
		jLabel3.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
		jLabel3.setText("����ѧԺ:");
		jLabel3.setBounds(new Rectangle(80, 100, 90, 20));
		jButton1.setBounds(new Rectangle(102, 223, 96, 29));
		jButton1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
		jButton1.setBorder(BorderFactory.createRaisedBevelBorder());
		jButton1.setText("��    ��");
		jButton1.addActionListener(new DepartU_jButton1_actionAdapter(this));
		jButton2.setBounds(new Rectangle(265, 221, 96, 31));
		jButton2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
		jButton2.setBorder(BorderFactory.createRaisedBevelBorder());
		jButton2.setToolTipText("");
		jButton2.setText("��    ��");
		jButton2.addActionListener(new DepartU_jButton2_actionAdapter(this));
		jOptionPane1.setBounds(new Rectangle(106, 258, 262, 90));
		jOptionPane1.setLayout(null);
		jComboBox1.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
		jComboBox1.setEditable(true);
		jComboBox1.setBounds(new Rectangle(180, 100, 180, 25));
		jComboBox1.addActionListener(new DepartU_jComboBox1_actionAdapter(this));
		jLabel4.setFont(new java.awt.Font("Dialog", Font.PLAIN, 18));
		jLabel4.setText("����רҵ��");
		jLabel4.setBounds(new Rectangle(80, 150, 90, 20));
		jComboBox2.setFont(new java.awt.Font("Dialog", Font.PLAIN, 16));
		jComboBox2.setEditable(true);
		jComboBox2.setBounds(new Rectangle(180, 150, 180, 25));
		contentPane.add(jLabel1);
		contentPane.add(jOptionPane1);
		contentPane.add(jComboBox1);
		contentPane.add(jLabel3);
		contentPane.add(jLabel4);
		contentPane.add(jComboBox2);
		contentPane.add(jButton2);
		contentPane.add(jButton1);
		jComboBox1.addItem("��ѡ��ѧԺ");
		// ѡ��ѧԺ
		try {
			ResultSet rs = conn.getRs("select * from tb_depart ");
			while (rs.next()) {
				String xibu = rs.getString("departName");
				jComboBox1.addItem(xibu);
			}

		} catch (Exception ce) {
			System.out.println("++++++++" + ce);
		}
		jComboBox2.setEnabled(false);
		xianshi();
	}

	// ѡ��רҵ
	public void zhuanye() {
		jComboBox2.removeAllItems();
		jComboBox2.addItem("��ѡ��רҵ");
		try {
			ResultSet rs = conn.getRs(
					"select * from tb_spec where departName='" + String.valueOf(jComboBox1.getSelectedItem()) + "' ");
			while (rs.next()) {
				String zhy = rs.getString("specName");
				jComboBox2.addItem(zhy);
			}
		} catch (Exception ce) {
			System.out.println("++++++++" + ce);
		}
	}

	// ����ѡ��ѧԺ��xueyuan
	public void xueYuan() {
		String sel = String.valueOf(jComboBox1.getSelectedItem());
		try {
			ResultSet rs = conn.getRs("select * from tb_depart where departName='" + sel + "'");
			while (rs.next()) {
				xueyuan = rs.getString("departName");
			}
			rs.close();
		} catch (Exception ce) {
			System.out.println("++++++++" + ce);
		}
	}

	// ����ѡ��רҵ��zhuanye
	public void zhuanYe() {
		String sel = String.valueOf(jComboBox2.getSelectedItem());
		try {
			ResultSet rs = conn.getRs("select * from tb_spec where specName='" + sel + "'");
			while (rs.next()) {
				zhuanye = rs.getString("specName");
			}
			rs.close();
		} catch (Exception ce) {
			System.out.println("++++++++" + ce);
		}
	}

	// �˳�
	public void jButton2_actionPerformed(ActionEvent e) {
		this.dispose();
	}

	// �޸�
	public void jButton1_actionPerformed(ActionEvent e) {
		// �쳣�ж�
		if (jComboBox1.getSelectedIndex() == 0) {
			jOptionPane1.showMessageDialog(this, "��ѡ������ѧԺ��", "��ʾ", JOptionPane.INFORMATION_MESSAGE, null);
		} else if (jComboBox2.getSelectedIndex() == 0) {
			jOptionPane1.showMessageDialog(this, "��ѡ������רҵ��", "��ʾ", JOptionPane.INFORMATION_MESSAGE, null);
		} else {
			xueYuan();
			zhuanYe();
			try {
				// ��ѧԺ��רҵ�ֱ������������������жϴ���
				boolean departExist = false, specExist = false;

				ResultSet rs_departExist = conn.getRs("select * from tb_spec where departName= '"
						+ String.valueOf(jComboBox1.getSelectedItem()) + "' ");
				while (rs_departExist.next()) {
					departExist = true;
				}

				ResultSet rs_specExist = conn.getRs("select * from tb_spec where specName= '"
						+ String.valueOf(jComboBox2.getSelectedItem()) + "' ");
				while (rs_specExist.next()) {
					specExist = true;
				}

				if (departExist && specExist) {
					jOptionPane1.showMessageDialog(this, "��ѧԺ��רҵ�Ѿ����ڣ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE, null);
				} else if (!departExist && specExist) {
					jOptionPane1.showMessageDialog(this, "��רҵ������ѧԺ�Ѿ����ڣ�", "��ʾ", JOptionPane.INFORMATION_MESSAGE, null);
				} else if (departExist && !specExist) {
					conn.getUpdate("delete from tb_spec where specId='" + Integer.valueOf(find) + "'");
					conn.getUpdate("insert into tb_spec (departName,specName) values ('"
							+ String.valueOf(jComboBox1.getSelectedItem()) + "','"
							+ String.valueOf(jComboBox2.getSelectedItem()) + "')");
					jOptionPane1.showMessageDialog(this, "��ϲ��Ժϵ��Ϣ�޸ĳɹ���", "��ʾ", JOptionPane.INFORMATION_MESSAGE, null);
				} else if (!departExist && !specExist) {
					conn.getUpdate("delete from tb_spec where specId='" + Integer.valueOf(find) + "'");
					conn.getUpdate("insert into tb_spec (departName,specName) values ('"
							+ String.valueOf(jComboBox1.getSelectedItem()) + "','"
							+ String.valueOf(jComboBox2.getSelectedItem()) + "')");
					conn.getUpdate("insert into tb_depart (departName) values ('"
							+ String.valueOf(jComboBox1.getSelectedItem()) + "') ");
					jOptionPane1.showMessageDialog(this, "��ϲ��Ժϵ��Ϣ�޸ĳɹ���", "��ʾ", JOptionPane.INFORMATION_MESSAGE, null);
				}
			} catch (Exception ce) {
				System.out.println(ce);
			}
		}
	}

	// ѡ�񴥷�
	public void jComboBox1_actionPerformed(ActionEvent e) {
		zhuanye();
		jComboBox2.setEnabled(true);
	}

	// ѡ���к����޸Ľ�����ʾѡ��Ĭ��ֵ
	public void xianshi() {
		if (find == null)
			return;
		else {
			jComboBox2.setEnabled(true);
			try {
				ResultSet rs = conn.getRs("select * from tb_spec where specId='" + Integer.valueOf(find) + "'");
				while (rs.next()) {
					jComboBox1.setSelectedItem(String.valueOf(rs.getString("departName")));
					jComboBox2.setSelectedItem(String.valueOf(rs.getString("specName")));
				}
				rs.close();
			} catch (Exception ce) {
				System.out.println("++++++++" + ce);
			}
		}
	}
}

class DepartU_jComboBox1_actionAdapter implements ActionListener {

	private DepartChange adaptee;

	DepartU_jComboBox1_actionAdapter(DepartChange adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jComboBox1_actionPerformed(e);
	}
}

class DepartU_jButton1_actionAdapter implements ActionListener {

	private DepartChange adaptee;

	DepartU_jButton1_actionAdapter(DepartChange adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButton1_actionPerformed(e);
	}
}

class DepartU_jButton2_actionAdapter implements ActionListener {

	private DepartChange adaptee;

	DepartU_jButton2_actionAdapter(DepartChange adaptee) {
		this.adaptee = adaptee;
	}

	public void actionPerformed(ActionEvent e) {
		adaptee.jButton2_actionPerformed(e);
	}
}