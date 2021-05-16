
package Test;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class MenuFileFolder extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	/**
	 * Launch the application.
	 */

	JPanel panelDirectory = new JPanel();
	JPanel panelFile = new JPanel();
	private final JLabel lblNewLabel = new JLabel("FOLDER");
	private final JTable table = new JTable();
	private final JScrollPane scrollPane_1 = new JScrollPane();
	private JTable tableFile;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MenuFileFolder frame = new MenuFileFolder("");
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public MenuFileFolder(String link) {

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 662, 571);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(2, 1));

		contentPane.add(panelDirectory);
		panelDirectory.setLayout(null);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblNewLabel.setBounds(270, 5, 99, 25);

		panelDirectory.add(lblNewLabel);
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				DefaultTableModel model = (DefaultTableModel) table.getModel();
				MenuFileFolder menu = new MenuFileFolder(link+"/"+model.getValueAt(table.getSelectedRow(), 0).toString());
				menu.setVisible(true);
			}
		});
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Name", "Owner", "Date Time" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setResizable(false);
		table.getColumnModel().getColumn(1).setResizable(false);
		table.getColumnModel().getColumn(2).setResizable(false);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(5, 30, 625, 230);
		panelDirectory.add(scrollPane);

		panelFile.setLayout(null);
		scrollPane_1.setBounds(5, 30, 625, 230);

		panelFile.add(scrollPane_1);
		tableFile = new JTable();
		tableFile.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Name", "Owner", "Date Time" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableFile.getColumnModel().getColumn(0).setResizable(false);
		tableFile.getColumnModel().getColumn(1).setResizable(false);
		tableFile.getColumnModel().getColumn(2).setResizable(false);
		scrollPane_1.setViewportView(tableFile);
		JLabel lblFiles = new JLabel("FILES");
		lblFiles.setHorizontalAlignment(SwingConstants.CENTER);
		lblFiles.setFont(new Font("Times New Roman", Font.PLAIN, 18));
		lblFiles.setBounds(270, 5, 99, 25);
		panelFile.add(lblFiles);
		contentPane.add(panelFile);

		FTPClient ftpClient = new FTPClient();
		try {
			String iplocalhost = InetAddress.getLocalHost().getHostAddress(); 
			ftpClient.connect(iplocalhost);
			ftpClient.login("FTP-user", "230920");
			ftpClient.enterLocalPassiveMode(); // use local passive mode to pass firewall
		} catch (SocketException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			FTPFile[] files1 = ftpClient.listFiles(link+"/");
			FTPList.printFileDetails(ftpClient, files1, table, tableFile);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
