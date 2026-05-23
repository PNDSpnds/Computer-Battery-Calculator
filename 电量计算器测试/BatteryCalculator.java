import javax.swing.*;
import javax.swing.plaf.basic.BasicProgressBarUI;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class BatteryCalculator {
    private JFrame frame;
    private JPanel mainPanel;
    private JLabel titleLabel;
    private JButton startBtn;
    private JProgressBar progressBar;
    private JLabel statusLabel;
    private JLabel resultLabel;
    private Timer timer;
    private int progressValue;

    public BatteryCalculator() {
        initUI();
    }

    private void initUI() {
        frame = new JFrame("电量计算器");
        frame.setSize(400, 280);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Blue tint: #f0f4ff
        Color bgColor = new Color(0xf0f4ff);
        Color primaryBlue = new Color(0x2563eb);
        Color titleBlue = new Color(0x1a56db);

        mainPanel = new JPanel();
        mainPanel.setBackground(bgColor);
        mainPanel.setLayout(new GridBagLayout());
        frame.add(mainPanel);

        Font yahei = new Font("Microsoft YaHei UI", Font.PLAIN, 13);

        // Title
        titleLabel = new JLabel("电量计算器", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 24));
        titleLabel.setForeground(titleBlue);

        // Start button (rounded flat style)
        startBtn = new JButton("开始计算");
        startBtn.setFont(yahei);
        startBtn.setBackground(primaryBlue);
        startBtn.setForeground(Color.WHITE);
        startBtn.setFocusPainted(false);
        startBtn.setBorderPainted(false);
        startBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        startBtn.setPreferredSize(new Dimension(160, 42));
        startBtn.addActionListener(e -> startCalculation());

        // Hover effect for button
        Color hoverBlue = new Color(0x1d4ed8);
        startBtn.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                startBtn.setBackground(hoverBlue);
            }
            public void mouseExited(MouseEvent e) {
                startBtn.setBackground(primaryBlue);
            }
        });

        // Progress bar
        progressBar = new JProgressBar(0, 100);
        progressBar.setPreferredSize(new Dimension(300, 20));
        progressBar.setStringPainted(false);
        progressBar.setForeground(primaryBlue);
        progressBar.setBackground(Color.WHITE);
        progressBar.setBorderPainted(false);
        progressBar.setUI(new BasicProgressBarUI() {
            protected Color getSelectionBackground() { return Color.WHITE; }
            protected Color getSelectionForeground() { return Color.WHITE; }
        });

        // Status label
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 11));
        statusLabel.setForeground(new Color(0x6b7280));

        // Result label
        resultLabel = new JLabel("", SwingConstants.CENTER);
        resultLabel.setFont(new Font("Microsoft YaHei UI", Font.BOLD, 18));
        resultLabel.setForeground(new Color(0x16a34a));

        // Layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(8, 20, 8, 20);

        gbc.gridy = 0;
        gbc.insets = new Insets(30, 20, 20, 20);
        mainPanel.add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.insets = new Insets(8, 60, 8, 60);
        mainPanel.add(startBtn, gbc);

        gbc.gridy = 2;
        gbc.insets = new Insets(15, 50, 5, 50);
        mainPanel.add(progressBar, gbc);

        gbc.gridy = 3;
        gbc.insets = new Insets(4, 20, 4, 20);
        mainPanel.add(statusLabel, gbc);

        gbc.gridy = 4;
        gbc.insets = new Insets(10, 20, 10, 20);
        mainPanel.add(resultLabel, gbc);

        // Hide progress/status/result initially
        progressBar.setVisible(false);
        statusLabel.setVisible(false);
        resultLabel.setVisible(false);

        // Center on screen
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private void startCalculation() {
        startBtn.setVisible(false);
        resultLabel.setVisible(false);
        resultLabel.setText("");

        progressBar.setValue(0);
        progressBar.setVisible(true);

        statusLabel.setText("正在通过AI大模型计算本机电量");
        statusLabel.setVisible(true);

        progressValue = 0;
        timer = new Timer(30, e -> {
            progressValue++;
            progressBar.setValue(progressValue);
            if (progressValue >= 100) {
                timer.stop();
                showResult();
            }
        });
        timer.start();
    }

    private void showResult() {
        progressBar.setVisible(false);
        statusLabel.setVisible(false);
        resultLabel.setText("有电");
        resultLabel.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(BatteryCalculator::new);
    }
}
