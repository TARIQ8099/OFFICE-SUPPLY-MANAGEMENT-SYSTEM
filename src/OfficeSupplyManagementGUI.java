import javax.swing.*;
import java.awt.*;
import java.util.List;
import javax.swing.table.DefaultTableModel;

// =============================
// 6. Main GUI
// =============================
public class OfficeSupplyManagementGUI extends JFrame {

    // Constants for CardLayout panel keys
    private static final String PANEL_ROLE_SELECTION = "RoleSelectionPanel";
    private static final String PANEL_ADMIN_LOGIN    = "AdminLoginPanel";
    private static final String PANEL_TEACHER_LOGIN  = "TeacherLoginPanel";
    private static final String PANEL_ADMIN          = "AdminPanel";
    private static final String PANEL_TEACHER        = "TeacherPanel";

    private CardLayout cardLayout;
    private JPanel mainPanel;

    // DAOs
    private TeacherDAO teacherDAO;
    private SupplyDAO supplyDAO;
    private RequisitionDAO requisitionDAO;

    // Data caches
    private List<Teacher> teacherList;
    private List<Supply> supplyList;
    private List<Requisition> requisitionList;

    // Hard-coded admin (with hashed password)
    private Administrator admin = new Administrator("001", "Admin", "001");

    // Panels
    private RoleSelectionPanel roleSelectionPanel;
    private AdminLoginPanel    adminLoginPanel;
    private TeacherLoginPanel  teacherLoginPanel;
    private AdminPanel         adminPanel;
    private TeacherPanel       teacherPanel;

    // Constructor
    public OfficeSupplyManagementGUI() {
        super("Office Supply Management");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Initialize DAOs
        teacherDAO     = new TeacherDAO();
        supplyDAO      = new SupplyDAO();
        requisitionDAO = new RequisitionDAO();

        // Load data from DB
        teacherList     = teacherDAO.getAllTeachers();
        supplyList      = supplyDAO.getAllSupplies();
        requisitionList = requisitionDAO.getAllRequisitions();

        cardLayout = new CardLayout();
        mainPanel  = new JPanel(cardLayout);

        // Create Panel Instances
        roleSelectionPanel = new RoleSelectionPanel();
        adminLoginPanel    = new AdminLoginPanel();
        teacherLoginPanel  = new TeacherLoginPanel();
        adminPanel         = new AdminPanel();
        teacherPanel       = new TeacherPanel();

        // Add Panels to CardLayout
        mainPanel.add(roleSelectionPanel, PANEL_ROLE_SELECTION);
        mainPanel.add(adminLoginPanel,    PANEL_ADMIN_LOGIN);
        mainPanel.add(teacherLoginPanel,  PANEL_TEACHER_LOGIN);
        mainPanel.add(adminPanel,         PANEL_ADMIN);
        mainPanel.add(teacherPanel,       PANEL_TEACHER);

        add(mainPanel);
        showPanel(PANEL_ROLE_SELECTION);
    }

    private void showPanel(String panelKey) {
        cardLayout.show(mainPanel, panelKey);
    }

    // -------------------------------------------
    // 6.1: RoleSelectionPanel
    // -------------------------------------------
    private class RoleSelectionPanel extends JPanel {
        public RoleSelectionPanel() {
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setBackground(new Color(245, 245, 245)); // Light gray background

            JLabel lblTitle = new JLabel("Select Your Role", SwingConstants.CENTER);
            lblTitle.setFont(new Font("Arial", Font.BOLD, 28));
            lblTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
            lblTitle.setBorder(BorderFactory.createEmptyBorder(30,0,30,0));
            add(lblTitle);

            JPanel btnPanel = new JPanel();
            btnPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 30, 0));
            btnPanel.setOpaque(false);

            JButton btnAdmin   = new JButton("Administrator");
            btnAdmin.setFont(new Font("Arial", Font.PLAIN, 16));
            JButton btnTeacher = new JButton("Teacher");
            btnTeacher.setFont(new Font("Arial", Font.PLAIN, 16));

            btnAdmin.addActionListener(e -> showPanel(PANEL_ADMIN_LOGIN));
            btnTeacher.addActionListener(e -> showPanel(PANEL_TEACHER_LOGIN));

            btnPanel.add(btnAdmin);
            btnPanel.add(btnTeacher);

            add(btnPanel);
        }
    }

    // -------------------------------------------
    // 6.2: AdminLoginPanel
    // -------------------------------------------
    private class AdminLoginPanel extends JPanel {
        private JTextField txtAdminId;
        private JPasswordField txtAdminPass;
        private JButton btnLogin, btnBack;

        public AdminLoginPanel() {
            setBackground(new Color(224, 255, 255));
            setLayout(null);

            JLabel lblTitle = new JLabel("Administrator Login", SwingConstants.CENTER);
            lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
            lblTitle.setBounds(200, 30, 400, 40);
            add(lblTitle);

            JLabel lblAdminId = new JLabel("Admin ID:");
            lblAdminId.setFont(new Font("Arial", Font.PLAIN, 14));
            lblAdminId.setBounds(250, 100, 100, 25);
            add(lblAdminId);

            txtAdminId = new JTextField();
            txtAdminId.setBounds(350, 100, 150, 25);
            add(txtAdminId);

            JLabel lblPassword = new JLabel("Password:");
            lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
            lblPassword.setBounds(250, 140, 100, 25);
            add(lblPassword);

            txtAdminPass = new JPasswordField();
            txtAdminPass.setBounds(350, 140, 150, 25);
            add(txtAdminPass);

            btnLogin = new JButton("Login");
            btnLogin.setFont(new Font("Arial", Font.PLAIN, 14));
            btnLogin.setBounds(300, 200, 80, 30);
            add(btnLogin);

            btnBack = new JButton("Back");
            btnBack.setFont(new Font("Arial", Font.PLAIN, 14));
            btnBack.setBounds(400, 200, 80, 30);
            add(btnBack);

            btnLogin.addActionListener(e -> {
                String id  = txtAdminId.getText().trim();
                String pwd = new String(txtAdminPass.getPassword()).trim();

                if (admin.getId().equals(id) && admin.authenticate(pwd)) {
                    showPanel(PANEL_ADMIN);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Invalid Admin credentials!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            });

            btnBack.addActionListener(e -> showPanel(PANEL_ROLE_SELECTION));
        }

        @Override
        public void setVisible(boolean aFlag) {
            super.setVisible(aFlag);
            if (aFlag) {
                txtAdminId.setText("");
                txtAdminPass.setText("");
            }
        }
    }

    // -------------------------------------------
    // 6.3: TeacherLoginPanel
    // -------------------------------------------
    private class TeacherLoginPanel extends JPanel {
        private JTextField txtTeacherId;
        private JPasswordField txtTeacherPass;
        private JButton btnLogin, btnBack;

        public TeacherLoginPanel() {
            setBackground(new Color(224, 255, 255));
            setLayout(null);

            JLabel lblTitle = new JLabel("Teacher Login", SwingConstants.CENTER);
            lblTitle.setFont(new Font("Arial", Font.BOLD, 24));
            lblTitle.setBounds(200, 30, 400, 40);
            add(lblTitle);

            JLabel lblTeacherId = new JLabel("Teacher ID:");
            lblTeacherId.setFont(new Font("Arial", Font.PLAIN, 14));
            lblTeacherId.setBounds(250, 100, 100, 25);
            add(lblTeacherId);

            txtTeacherId = new JTextField();
            txtTeacherId.setBounds(350, 100, 150, 25);
            add(txtTeacherId);

            JLabel lblPassword = new JLabel("Password:");
            lblPassword.setFont(new Font("Arial", Font.PLAIN, 14));
            lblPassword.setBounds(250, 140, 100, 25);
            add(lblPassword);

            txtTeacherPass = new JPasswordField();
            txtTeacherPass.setBounds(350, 140, 150, 25);
            add(txtTeacherPass);

            btnLogin = new JButton("Login");
            btnLogin.setFont(new Font("Arial", Font.PLAIN, 14));
            btnLogin.setBounds(300, 200, 80, 30);
            add(btnLogin);

            btnBack = new JButton("Back");
            btnBack.setFont(new Font("Arial", Font.PLAIN, 14));
            btnBack.setBounds(400, 200, 80, 30);
            add(btnBack);

            btnLogin.addActionListener(e -> {
                String id  = txtTeacherId.getText().trim();
                String pwd = new String(txtTeacherPass.getPassword()).trim();

                Teacher found = null;
                for (Teacher t : teacherList) {
                    if (t.getId().equals(id) && t.authenticate(pwd)) {
                        found = t;
                        break;
                    }
                }
                if (found != null) {
                    teacherPanel.setLoggedTeacher(found);
                    JOptionPane.showMessageDialog(
                            this,
                            "Welcome, " + found.getName() + "!",
                            "Teacher Logged In",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    showPanel(PANEL_TEACHER);
                } else {
                    JOptionPane.showMessageDialog(
                            this,
                            "Invalid Teacher credentials!",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            });

            btnBack.addActionListener(e -> showPanel(PANEL_ROLE_SELECTION));
        }

        @Override
        public void setVisible(boolean aFlag) {
            super.setVisible(aFlag);
            if (aFlag) {
                txtTeacherId.setText("");
                txtTeacherPass.setText("");
            }
        }
    }

    // -------------------------------------------
    // 6.4: AdminPanel & TeacherPanel
    // -------------------------------------------
    private class AdminPanel extends JPanel {
        private JTabbedPane tabbedPane;

        public AdminPanel() {
            setLayout(new BorderLayout());
            tabbedPane = new JTabbedPane();

            tabbedPane.addTab("Teacher Management", new TeacherManagementPanel());
            tabbedPane.addTab("Supplies Management", new SuppliesManagementPanel());
            tabbedPane.addTab("Approve Requisitions", new ApproveRequisitionsPanel());

            JButton btnLogout = new JButton("Logout");
            btnLogout.setFont(new Font("Arial", Font.BOLD, 14));
            btnLogout.addActionListener(e -> showPanel(PANEL_ROLE_SELECTION));

            add(tabbedPane, BorderLayout.CENTER);
            add(btnLogout, BorderLayout.SOUTH);
        }

        // ========== Teacher Management ==========
        private class TeacherManagementPanel extends JPanel {
            private JTextField txtId, txtName, txtDepartment, txtPassword;
            private JButton btnAdd, btnDelete, btnRefresh, btnViewTable;
            private DefaultListModel<String> teacherListModel;
            private JList<String> teacherListView;

            public TeacherManagementPanel() {
                setLayout(new BorderLayout());

                JPanel formPanel = new JPanel(new GridLayout(5,2,5,5));
                formPanel.setBorder(BorderFactory.createTitledBorder("Add Teacher"));
                formPanel.add(new JLabel("ID:"));
                txtId = new JTextField();
                formPanel.add(txtId);
                formPanel.add(new JLabel("Name:"));
                txtName = new JTextField();
                formPanel.add(txtName);
                formPanel.add(new JLabel("Department:"));
                txtDepartment = new JTextField();
                formPanel.add(txtDepartment);
                formPanel.add(new JLabel("Password:"));
                txtPassword = new JTextField();
                formPanel.add(txtPassword);

                btnAdd = new JButton("Add Teacher");
                formPanel.add(btnAdd);
                formPanel.add(new JLabel("")); // filler

                add(formPanel, BorderLayout.NORTH);

                teacherListModel = new DefaultListModel<>();
                teacherListView  = new JList<>(teacherListModel);
                JScrollPane scrollPane = new JScrollPane(teacherListView);
                scrollPane.setBorder(BorderFactory.createTitledBorder("Teacher List"));
                add(scrollPane, BorderLayout.CENTER);

                JPanel actionPanel = new JPanel();
                btnDelete    = new JButton("Delete Selected");
                btnRefresh   = new JButton("Refresh");
                btnViewTable = new JButton("View Table");

                actionPanel.add(btnDelete);
                actionPanel.add(btnRefresh);
                actionPanel.add(btnViewTable);
                add(actionPanel, BorderLayout.SOUTH);

                refreshTeacherListDisplay();

                btnAdd.addActionListener(e -> {
                    String id   = txtId.getText().trim();
                    String name = txtName.getText().trim();
                    String dept = txtDepartment.getText().trim();
                    String pwd  = txtPassword.getText().trim();

                    if (id.isEmpty() || name.isEmpty() || dept.isEmpty() || pwd.isEmpty()) {
                        JOptionPane.showMessageDialog(
                                this,
                                "All fields are required.",
                                "Warning",
                                JOptionPane.WARNING_MESSAGE
                        );
                        return;
                    }

                    Teacher newT = new Teacher(id, name, dept, pwd);

                    try {
                        teacherDAO.insertTeacher(newT);
                        teacherList.clear();
                        teacherList.addAll(teacherDAO.getAllTeachers());
                        refreshTeacherListDisplay();

                        txtId.setText("");
                        txtName.setText("");
                        txtDepartment.setText("");
                        txtPassword.setText("");

                    } catch (IllegalArgumentException ex) {
                        JOptionPane.showMessageDialog(
                                this,
                                ex.getMessage(),
                                "Invalid Input",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                });

                btnDelete.addActionListener(e -> {
                    String selected = teacherListView.getSelectedValue();
                    if (selected == null) return;
                    String[] parts = selected.split(" - ");
                    if (parts.length > 0) {
                        String idPart   = parts[0];
                        String teacherId= idPart.replace("ID: ", "");
                        boolean success = teacherDAO.deleteTeacherById(teacherId);
                        if (success) {
                            teacherList.removeIf(t -> t.getId().equals(teacherId));
                            refreshTeacherListDisplay();
                        } else {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Delete failed or teacher not found (possibly referencing requisitions).",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                        }
                    }
                });

                btnRefresh.addActionListener(e -> {
                    teacherList.clear();
                    teacherList.addAll(teacherDAO.getAllTeachers());
                    refreshTeacherListDisplay();
                });

                btnViewTable.addActionListener(e -> showTeacherTableDialog());
            }

            private void refreshTeacherListDisplay() {
                teacherListModel.clear();
                for (Teacher t : teacherList) {
                    teacherListModel.addElement(
                            "ID: " + t.getId() +
                                    " - Name: " + t.getName() +
                                    " - Dept: " + t.getDepartment()
                    );
                }
            }

            private void showTeacherTableDialog() {
                JDialog dialog = new JDialog(
                        SwingUtilities.getWindowAncestor(this),
                        "All Teachers (Table View)",
                        Dialog.ModalityType.APPLICATION_MODAL
                );
                dialog.setSize(600, 300);
                dialog.setLocationRelativeTo(this);

                String[] columns = {"Teacher ID", "Name", "Department", "Hashed Password"};
                Object[][] data = new Object[teacherList.size()][4];
                for (int i = 0; i < teacherList.size(); i++) {
                    Teacher t = teacherList.get(i);
                    data[i][0] = t.getId();
                    data[i][1] = t.getName();
                    data[i][2] = t.getDepartment();
                    data[i][3] = t.password;
                }

                DefaultTableModel model = new DefaultTableModel(data, columns);
                JTable table = new JTable(model);
                dialog.add(new JScrollPane(table), BorderLayout.CENTER);

                dialog.setVisible(true);
            }
        }

        // ========== Supplies Management ==========
        private class SuppliesManagementPanel extends JPanel {
            private JTextField txtName, txtQuantity;
            private JButton btnAdd, btnDelete, btnRefresh, btnViewTable;
            private DefaultListModel<String> supplyListModel;
            private JList<String> supplyListView;

            public SuppliesManagementPanel() {
                setLayout(new BorderLayout());

                JPanel topPanel = new JPanel(new GridLayout(3,2,5,5));
                topPanel.setBorder(BorderFactory.createTitledBorder("Add Supply"));
                topPanel.add(new JLabel("Name:"));
                txtName = new JTextField();
                topPanel.add(txtName);
                topPanel.add(new JLabel("Quantity:"));
                txtQuantity = new JTextField();
                topPanel.add(txtQuantity);

                btnAdd      = new JButton("Add Supply");
                btnDelete   = new JButton("Delete Selected");
                btnViewTable= new JButton("View Table");
                topPanel.add(btnAdd);
                topPanel.add(btnDelete);

                add(topPanel, BorderLayout.NORTH);

                supplyListModel = new DefaultListModel<>();
                supplyListView  = new JList<>(supplyListModel);
                JScrollPane scroll = new JScrollPane(supplyListView);
                scroll.setBorder(BorderFactory.createTitledBorder("Supplies"));
                add(scroll, BorderLayout.CENTER);

                btnRefresh = new JButton("Refresh");

                JPanel bottomPanel = new JPanel(new FlowLayout());
                bottomPanel.add(btnRefresh);
                bottomPanel.add(btnViewTable);
                add(bottomPanel, BorderLayout.SOUTH);

                refreshSupplyListDisplay();

                btnAdd.addActionListener(e -> {
                    try {
                        String name = txtName.getText().trim();
                        int qty = Integer.parseInt(txtQuantity.getText().trim());
                        if (name.isEmpty() || qty < 0) {
                            JOptionPane.showMessageDialog(
                                    this,
                                    "Invalid input.",
                                    "Error",
                                    JOptionPane.ERROR_MESSAGE
                            );
                            return;
                        }
                        Supply s = new Supply(name, qty, "Not Requisitioned");
                        supplyDAO.insertSupply(s);

                        supplyList.add(s);
                        refreshSupplyListDisplay();

                        txtName.setText("");
                        txtQuantity.setText("");
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Invalid quantity.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                });

                btnDelete.addActionListener(e -> {
                    String selected = supplyListView.getSelectedValue();
                    if (selected == null) return;
                    int start = selected.indexOf("id=") + 3;
                    int end   = selected.indexOf(",", start);
                    String idStr = selected.substring(start, end);
                    int supplyId = Integer.parseInt(idStr.trim());

                    boolean success = supplyDAO.deleteSupplyById(supplyId);
                    if (success) {
                        supplyList.removeIf(sp -> sp.getSupplyId() == supplyId);
                        refreshSupplyListDisplay();
                    } else {
                        JOptionPane.showMessageDialog(
                                this,
                                "Delete failed or supply not found.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                    }
                });

                btnRefresh.addActionListener(e -> {
                    supplyList.clear();
                    supplyList.addAll(supplyDAO.getAllSupplies());
                    refreshSupplyListDisplay();
                });

                btnViewTable.addActionListener(e -> showSupplyTableDialog());
            }

            private void refreshSupplyListDisplay() {
                supplyListModel.clear();
                for (Supply s : supplyList) {
                    supplyListModel.addElement(s.toString());
                }
            }

            private void showSupplyTableDialog() {
                JDialog dialog = new JDialog(
                        SwingUtilities.getWindowAncestor(this),
                        "All Supplies (Table View)",
                        Dialog.ModalityType.APPLICATION_MODAL
                );
                dialog.setSize(600, 300);
                dialog.setLocationRelativeTo(this);

                String[] columns = {"Supply ID", "Name", "Quantity", "Status"};
                Object[][] data = new Object[supplyList.size()][4];
                for (int i = 0; i < supplyList.size(); i++) {
                    Supply s = supplyList.get(i);
                    data[i][0] = s.getSupplyId();
                    data[i][1] = s.getSupplyName();
                    data[i][2] = s.getQuantity();
                    data[i][3] = s.getStatus();
                }

                DefaultTableModel model = new DefaultTableModel(data, columns);
                JTable table = new JTable(model);
                dialog.add(new JScrollPane(table), BorderLayout.CENTER);

                dialog.setVisible(true);
            }
        }

        // ========== Approve Requisitions ==========
        private class ApproveRequisitionsPanel extends JPanel {
            private DefaultListModel<String> reqListModel;
            private JList<String> reqListView;
            private JButton btnApprove, btnDecline, btnRefresh, btnViewTable;

            public ApproveRequisitionsPanel() {
                setLayout(new BorderLayout());

                reqListModel = new DefaultListModel<>();
                reqListView  = new JList<>(reqListModel);
                JScrollPane scroll = new JScrollPane(reqListView);
                scroll.setBorder(BorderFactory.createTitledBorder("All Requisitions"));
                add(scroll, BorderLayout.CENTER);

                JPanel bottom = new JPanel();
                btnApprove   = new JButton("Approve Selected");
                btnDecline   = new JButton("Decline Selected");
                btnRefresh   = new JButton("Refresh");
                btnViewTable = new JButton("View Table");
                bottom.add(btnApprove);
                bottom.add(btnDecline);
                bottom.add(btnRefresh);
                bottom.add(btnViewTable);
                add(bottom, BorderLayout.SOUTH);

                refreshRequisitionListDisplay();

                // APPROVE
                btnApprove.addActionListener(e -> {
                    int[] indices = reqListView.getSelectedIndices();
                    if (indices.length == 0) return;

                    for (int i = indices.length - 1; i >= 0; i--) {
                        int index = indices[i];
                        Requisition r = requisitionList.get(index);

                        // 1) Find the related supply
                        Supply relatedSupply = null;
                        for (Supply s : supplyList) {
                            if (s.getSupplyId() == r.getSupplyId()) {
                                relatedSupply = s;
                                break;
                            }
                        }
                        // 2) Update supply status to "Not Requisitioned"
                        if (relatedSupply != null) {
                            // For Approve, we do NOT revert supply quantity
                            relatedSupply.setStatus("Not Requisitioned");
                            supplyDAO.updateSupply(relatedSupply);
                        }

                        // 3) Instead of removing from DB, set Requisition status to "Approved Requisition"
                        r.setStatus("Approved Requisition");
                        requisitionDAO.updateRequisition(r);

                        // We do NOT call deleteRequisitionById(r.getRequisitionId()) here.
                        // So the teacher can still see it in My Requisitions with the updated status.
                    }

                    // 4) Reload supplyList from DB so "Supplies Management" sees updated statuses
                    supplyList.clear();
                    supplyList.addAll(supplyDAO.getAllSupplies());

                    JOptionPane.showMessageDialog(
                            this,
                            "The request(s) have been approved.",
                            "Request Approved",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    refreshRequisitionListDisplay();
                });

                // DECLINE
                btnDecline.addActionListener(e -> {
                    int[] indices = reqListView.getSelectedIndices();
                    if (indices.length == 0) return;

                    for (int i = indices.length - 1; i >= 0; i--) {
                        int index = indices[i];
                        Requisition r = requisitionList.get(index);

                        // 1) Find the related supply
                        Supply relatedSupply = null;
                        for (Supply s : supplyList) {
                            if (s.getSupplyId() == r.getSupplyId()) {
                                relatedSupply = s;
                                break;
                            }
                        }
                        // 2) Revert supply quantity and set supply status to "Not Requisitioned"
                        if (relatedSupply != null) {
                            relatedSupply.setQuantity(relatedSupply.getQuantity() + r.getReqQuantity());
                            relatedSupply.setStatus("Not Requisitioned");
                            supplyDAO.updateSupply(relatedSupply);
                        }

                        // 3) Instead of removing from DB, set Requisition status to "Declined Requisition"
                        r.setStatus("Declined Requisition");
                        requisitionDAO.updateRequisition(r);

                        // We do NOT call deleteRequisitionById(r.getRequisitionId()) here.
                        // So the teacher can still see the final "Declined" status in My Requisitions.
                    }

                    // 4) Reload supplyList from DB
                    supplyList.clear();
                    supplyList.addAll(supplyDAO.getAllSupplies());

                    JOptionPane.showMessageDialog(
                            this,
                            "The request(s) have been declined. The supply quantities have been updated.",
                            "Requisition Declined",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    refreshRequisitionListDisplay();
                });

                // REFRESH
                btnRefresh.addActionListener(e -> {
                    requisitionList.clear();
                    requisitionList.addAll(requisitionDAO.getAllRequisitions());
                    refreshRequisitionListDisplay();
                });

                // VIEW TABLE
                btnViewTable.addActionListener(e -> showRequisitionTableDialog());
            }

            private void refreshRequisitionListDisplay() {
                reqListModel.clear();
                for (Requisition r : requisitionList) {
                    reqListModel.addElement(r.toString());
                }
            }

            private void showRequisitionTableDialog() {
                JDialog dialog = new JDialog(
                        SwingUtilities.getWindowAncestor(this),
                        "All Requisitions (Table View)",
                        Dialog.ModalityType.APPLICATION_MODAL
                );
                dialog.setSize(700, 300);
                dialog.setLocationRelativeTo(this);

                String[] columns = {"Req ID", "Teacher ID", "Supply ID", "Quantity", "Status"};
                Object[][] data = new Object[requisitionList.size()][5];
                for (int i = 0; i < requisitionList.size(); i++) {
                    Requisition r = requisitionList.get(i);
                    data[i][0] = r.getRequisitionId();
                    data[i][1] = r.getTeacherId();
                    data[i][2] = r.getSupplyId();
                    data[i][3] = r.getReqQuantity();
                    data[i][4] = r.getStatus();
                }

                DefaultTableModel model = new DefaultTableModel(data, columns);
                JTable table = new JTable(model);
                dialog.add(new JScrollPane(table), BorderLayout.CENTER);

                dialog.setVisible(true);
            }
        }
    }

    // -------------------------------------------
    // 6.3: TeacherPanel
    // -------------------------------------------
    private class TeacherPanel extends JPanel {
        private Teacher loggedTeacher;
        private JTabbedPane tabbedPane;
        private JButton btnLogout;

        public TeacherPanel() {
            setLayout(new BorderLayout());
            tabbedPane = new JTabbedPane();
            tabbedPane.addTab("Browse Supplies", new BrowseSuppliesPanel());
            tabbedPane.addTab("Requisition Supplies", new RequisitionSuppliesPanel());
            tabbedPane.addTab("My Requisitions", new MyRequisitionsPanel());

            btnLogout = new JButton("Logout");
            btnLogout.setFont(new Font("Arial", Font.BOLD, 14));
            btnLogout.addActionListener(e -> {
                loggedTeacher = null;
                showPanel(PANEL_ROLE_SELECTION);
            });

            add(tabbedPane, BorderLayout.CENTER);
            add(btnLogout, BorderLayout.SOUTH);
        }

        public void setLoggedTeacher(Teacher t) {
            this.loggedTeacher = t;
        }

        // 6.3.1 Browse Supplies
        private class BrowseSuppliesPanel extends JPanel {
            private DefaultListModel<String> model;
            private JList<String> listView;

            public BrowseSuppliesPanel() {
                setLayout(new BorderLayout());
                model = new DefaultListModel<>();
                listView = new JList<>(model);
                JScrollPane scroll = new JScrollPane(listView);
                scroll.setBorder(BorderFactory.createTitledBorder("All Supplies"));
                add(scroll, BorderLayout.CENTER);
            }

            @Override
            public void setVisible(boolean visible) {
                super.setVisible(visible);
                if (visible) {
                    refreshList();
                }
            }

            private void refreshList() {
                model.clear();
                for (Supply s : supplyList) {
                    // Decide "Available" vs. "Unavailable" purely for display (do NOT alter the object in memory).
                    String availability = (s.getQuantity() > 0) ? "Available" : "Unavailable";
                    // Build the display string showing that availability:
                    String display = String.format(
                            "Supply{id=%d, name='%s', quantity=%d, status='%s'}",
                            s.getSupplyId(),
                            s.getSupplyName(),
                            s.getQuantity(),
                            availability
                    );
                    model.addElement(display);
                }
            }
        }

        // 6.3.2 Requisition Supplies
        private class RequisitionSuppliesPanel extends JPanel {
            private JComboBox<Supply> comboSupplies;
            private JTextField txtQuantity;
            private JButton btnRequisition;

            public RequisitionSuppliesPanel() {
                setLayout(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.insets = new Insets(5, 5, 5, 5);
                gbc.fill = GridBagConstraints.HORIZONTAL;

                gbc.gridx = 0;
                gbc.gridy = 0;
                add(new JLabel("Select Supply:"), gbc);

                comboSupplies = new JComboBox<>();
                // ↓↓↓ ADD THIS RENDERER ↓↓↓
                comboSupplies.setRenderer(new DefaultListCellRenderer() {
                    @Override
                    public Component getListCellRendererComponent(
                            JList<?> list, Object value, int index,
                            boolean isSelected, boolean cellHasFocus) {

                        // Let the default label setup run first:
                        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                        // Then, if it's a Supply, show status as "Available" or "Unavailable":
                        if (value instanceof Supply) {
                            Supply s = (Supply) value;
                            String availability = (s.getQuantity() > 0) ? "Available" : "Unavailable";
                            setText(String.format("Supply{id=%d, name='%s', quantity=%d, status='%s'}",
                                    s.getSupplyId(), s.getSupplyName(), s.getQuantity(), availability));
                        }
                        return this;
                    }
                });
                // ↑↑↑ END OF RENDERER ↑↑↑

                gbc.gridx = 1;
                add(comboSupplies, gbc);

                gbc.gridx = 0;
                gbc.gridy = 1;
                add(new JLabel("Quantity:"), gbc);

                txtQuantity = new JTextField(10);
                gbc.gridx = 1;
                add(txtQuantity, gbc);

                btnRequisition = new JButton("Requisition");
                gbc.gridx = 0;
                gbc.gridy = 2;
                gbc.gridwidth = 2;
                add(btnRequisition, gbc);

                btnRequisition.addActionListener(e -> {
                    Supply selected = (Supply) comboSupplies.getSelectedItem();
                    if (selected == null) return;

                    int reqQty;
                    try {
                        reqQty = Integer.parseInt(txtQuantity.getText().trim());
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Invalid quantity.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }

                    if (reqQty <= 0 || reqQty > selected.getQuantity()) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Quantity not available or invalid.",
                                "Error",
                                JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }

                    // Decrease supply quantity
                    selected.setQuantity(selected.getQuantity() - reqQty);
                    selected.setStatus("Pending Requisition");
                    supplyDAO.updateSupply(selected);

                    // Create new Requisition
                    Requisition r = new Requisition(
                            loggedTeacher.getId(),
                            selected.getSupplyId(),
                            reqQty,
                            "Pending Requisition"
                    );
                    requisitionDAO.insertRequisition(r);
                    requisitionList.add(r);

                    JOptionPane.showMessageDialog(
                            this,
                            "Requisition successful!",
                            "Info",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    populateCombo();
                    txtQuantity.setText("");
                });
            }

            @Override
            public void setVisible(boolean visible) {
                super.setVisible(visible);
                if (visible) {
                    populateCombo();
                }
            }

            private void populateCombo() {
                comboSupplies.removeAllItems();
                for (Supply s : supplyList) {
                    if (s.getQuantity() > 0) {
                        comboSupplies.addItem(s);
                    }
                }
            }
        }


        // 6.3.3 My Requisitions
        private class MyRequisitionsPanel extends JPanel {
            private DefaultListModel<String> model;
            private JList<String> listView;
            private JButton btnRefresh, btnDelete;
            private JPanel buttonPanel;

            public MyRequisitionsPanel() {
                setLayout(new BorderLayout());
                model    = new DefaultListModel<>();
                listView = new JList<>(model);
                JScrollPane scroll = new JScrollPane(listView);
                scroll.setBorder(BorderFactory.createTitledBorder("My Requisitions"));
                add(scroll, BorderLayout.CENTER);

                buttonPanel = new JPanel(new FlowLayout());
                btnRefresh  = new JButton("Refresh");
                btnDelete   = new JButton("Delete Requisitions");
                buttonPanel.add(btnRefresh);
                buttonPanel.add(btnDelete);

                add(buttonPanel, BorderLayout.SOUTH);

                btnRefresh.addActionListener(e -> refreshList());

                btnDelete.addActionListener(e -> {
                    int[] indices = listView.getSelectedIndices();
                    if (indices.length == 0) {
                        JOptionPane.showMessageDialog(
                                this,
                                "Please select at least one requisition to delete.",
                                "No Selection",
                                JOptionPane.WARNING_MESSAGE
                        );
                        return;
                    }

                    for (int i = indices.length - 1; i >= 0; i--) {
                        int index = indices[i];
                        String text = model.getElementAt(index);
                        Requisition r = findRequisitionByString(text);
                        if (r != null) {
                            // revert supply quantity
                            Supply supplyObj = findSupplyById(r.getSupplyId());
                            if (supplyObj != null) {
                                supplyObj.setQuantity(supplyObj.getQuantity() + r.getReqQuantity());
                                supplyObj.setStatus("Not Requisitioned");
                                supplyDAO.updateSupply(supplyObj);
                            }
                            // remove the Requisition
                            boolean success = requisitionDAO.deleteRequisitionById(r.getRequisitionId());
                            if (success) {
                                requisitionList.remove(r);
                            }
                        }
                    }

                    JOptionPane.showMessageDialog(
                            this,
                            "Selected requisitions have been deleted. The supply quantity has been reverted.",
                            "Requisition Deleted",
                            JOptionPane.INFORMATION_MESSAGE
                    );

                    refreshList();
                });
            }

            @Override
            public void setVisible(boolean visible) {
                super.setVisible(visible);
                if (visible && loggedTeacher != null) {
                    refreshList();
                }
            }

            private void refreshList() {
                model.clear();
                for (Requisition r : requisitionList) {
                    // Only consider this teacher's requisitions
                    if (r.getTeacherId().equals(loggedTeacher.getId())) {

                        String actualStatus = r.getStatus();
                        String finalStatus;

                        // Decide exactly which status to show
                        if ("Approved Requisition".equalsIgnoreCase(actualStatus)) {
                            finalStatus = "Approved Requisition";
                        } else if ("Declined Requisition".equalsIgnoreCase(actualStatus)) {
                            finalStatus = "Declined Requisition";
                        } else {
                            // If it's null, "Pending Requisition", or anything else,
                            // we display it as "Pending Requisition".
                            finalStatus = "Pending Requisition";
                        }

                        // Construct the display string using finalStatus
                        String displayText = String.format(
                                "Requisition{id=%d, teacher_id='%s', supply_id=%d, quantity=%d, status='%s'}",
                                r.getRequisitionId(),
                                r.getTeacherId(),
                                r.getSupplyId(),
                                r.getReqQuantity(),
                                finalStatus
                        );

                        model.addElement(displayText);
                    }
                }
            }

            private Requisition findRequisitionByString(String str) {
                int start = str.indexOf("id=") + 3;
                int end   = str.indexOf(",", start);
                if (start == -1 || end == -1) return null;
                String idStr = str.substring(start, end).trim();
                try {
                    int rid = Integer.parseInt(idStr);
                    for (Requisition req : requisitionList) {
                        if (req.getRequisitionId() == rid) {
                            return req;
                        }
                    }
                } catch (NumberFormatException e) {
                    return null;
                }
                return null;
            }

            private Supply findSupplyById(int supplyId) {
                for (Supply s : supplyList) {
                    if (s.getSupplyId() == supplyId) {
                        return s;
                    }
                }
                return null;
            }
        }
    }

    // =======================
    // 7. Main
    // =======================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            OfficeSupplyManagementGUI gui = new OfficeSupplyManagementGUI();
            gui.setVisible(true);
        });
    }
}
