package com.workfront.internship.workflow.entity;

import java.util.Arrays;
import java.util.List;

public enum AppArea {
    REPORTING(1, "Reporting", "Some rep description", "Team1"),
    API(2, "API", "API", "Team1"),
    ACCESSLEVELSSECURITY(3, "Access Levels Security", "Access Levels Security", "Team1"),
    AGILE(4, "Agile", "Agile", "Team1"),
    ANNOUNCEMENTCENTER(5, "Announcement Center", "Announcement Center", "Team1"),
    APPLICATIONSERVER(6, "Application Server", "Application Server", "Team1"),
    APPROVALS(7, "Approvals", "Approvals", "Team1"),
    ATTASKRELEASEPROCESS(8, "AtTask Release Process", "AtTask Release Process", "Team1"),
    AUDITLOGS(9, "Audit Logs", "Audit Logs", "Team1"),
    AUTOMATEDWORKFLOW(10, "Automated Workflow", "Automated Workflow", "Team1"),
    BACKOFFICEBACKEND(11, "Back Office Back End", "Back Office Back End", "Team1"),
    BILLING(12, "Billing", "Billing", "Team1"),
    BRANDING(13, "Branding", "Branding", "Team1"),
    BULKEDIT(14, "Bulk Edit", "Bulk Edit", "Team1"),
    BUSINESSCASE(15, "Business Case", "Business Case", "Team1"),
    BUSINESSUNIT(16, "Business Unit", "Business Unit", "Team1"),
    CACHING(17, "Caching", "Caching", "Team1"),
    CACHINGHAZELCASTKAFKA(18, "Caching (Hazelcast, Kafka)", "Caching (Hazelcast, Kafka)", "Team1"),
    CALENDAR(19, "Calendar", "Calendar", "Team1"),
    CAPACITYPLANNER(20, "Capacity Planner", "Capacity Planner", "Team1"),
    CLIK(21, "Clik", "Clik", "Team1"),
    CLONING(22, "Cloning", "Cloning", "Team1"),
    COMPANIES(23, "Companies", "Companies", "Team1"),
    CONFIGANDSETUP(24, "Config. and Setup", "Config. and Setup", "Team1"),
    CUSTOMDATA(25, "Custom Data", "Custom Data", "Team1"),
    CUSTOMFROMBUILDER(26, "Custom Form Builder", "Custom Form Builder", "Team1"),
    CUSTOMTABS(27, "Custom Tabs", "Custom Tabs", "Team1"),
    CUSTOMVIEWS(28, "Custom Views", "Custom Views", "Team1"),
    DASHBOARDS(29, "Dashboards", "Dashboards", "Team1"),
    DATA(30, "Data", "Data", "Team1"),
    DATAMAPPING(31, "Data Mapping", "Data Mapping", "Team1"),
    DATAGRID(32, "DataGrid", "DataGrid", "Team1"),
    DATABASEUPGRADE(33, "Database Upgrade", "Database Upgrade", "Team1"),
    DIAGNOSTICS(34, "Diagnostics", "Diagnostics", "Team1"),
    DOCUMENTINTEGRATIONS(35, "Document Integrations", "Document Integrations", "Team1"),
    DOCUMENTS(36, "Documents", "Documents", "Team1"),
    EMEADCBETAPROGRAM(37, "EMEA DC Beta Program", "EMEA DC Beta Program", "Team1"),
    ECHO(38, "Echo", "Echo", "Team1"),
    ELOQUA(39, "Eloqua", "Eloqua", "Team1"),
    EMAILPROCESSING(40, "Email Processing", "Email Processing", "Team1"),
    EMAILTEMPLATES(41, "Email Templates", "Email Templates", "Team1"),
    ENDORSEMENTSANDLIKES(42, "Endorsements & Likes", "Endorsements & Likes", "Team1"),
    EXACTTARGET(43, "Exact Target", "Exact Target", "Team1"),
    EXPORT(44, "Export", "Export", "Team1"),
    FILTERS(45, "Filters", "Filters", "Team1"),
    FINANCE(46, "Finance", "Finance", "Team1"),
    FINANCEANDCURRENCY(47, "Finance & Currency", "Finance & Currency", "Team1"),
    FLASH(48, "Flash", "Flash", "Team1"),
    FRONTENDFRAMEWORKS(49, "Front End Framework(s)", "Front End Framework(s)", "Team1"),
    FUSIONCHARTS(50, "Fusion Charts", "Fusion Charts", "Team1"),
    GANTT(51, "Gantt", "Gantt", "Team1"),
    GROUPINGS(52, "Groupings", "Groupings", "Team1"),
    HELPDESK(53, "Help Desk", "Help Desk", "Team1"),
    HELPSITE(54, "Help Site", "Help Site", "Team1"),
    IMAGESERVER(55, "Image Server", "Image Server", "Team1"),
    INLINEEDIT(56, "In-Line Edit", "In-Line Edit", "Team1"),
    INSTLLER(57, "Installer", "Installer", "Team1"),
    INTEGRATIONS(58, "Integrations", "Integrations", "Team1"),
    INTERNALINTEGRATIONSRACHEANDSAMANTHA(59, "Internal Integrations (Rachel & Samantha)",
            "Internal Integrations (Rachel & Samantha)", "Team1"),
    INTERNALPROCESSES(60, "Internal Processes", "Internal Processes", "Team1"),
    ISSUEDETAIL(61, "Issue Detail", "Issue Detail", "Team1"),
    KICKSTART(62, "Kick Start", "Kick Start", "Team1"),
    LDAP(63, "LDAP", "LDAP", "Team1"),
    LAYOUTTEMPLATES(64, "Layout Templates", "Layout Templates", "Team1"),
    LEFTNAVIGATION(65, "Left Navigation", "Left Navigation", "Team1"),
    LEGACY(66, "Legacy", "Legacy", "Team1"),
    LISTS(67, "Lists", "Lists", "Team1"),
    LOGIN(68, "Login", "Login", "Team1"),
    MLM(69, "MLM", "MLM", "Team1"),
    MSPROJECTIMPORT(70, "MS Project Import", "MS Project Import", "Team1"),
    MSPINTEGRATION(71, "MSP Integration", "MSP Integration", "Team1"),
    MESSAGINGAMQHORNETQKAFKA(72, "Messaging (AMQ, HornetQ, Kafka)",
            "Messaging (AMQ, HornetQ, Kafka)", "Team1"),
    MIGRATION(73, "Migration", "Migration", "Team1"),
    MILESTONES(74, "Milestones", "Milestones", "Team1"),
    MOBILE(75, "Mobile", "Mobile", "Team1"),
    MOBILENOTIFICATIONS(76, "Mobile Notifications", "Mobile Notifications", "Team1"),
    MULTICURRENCY(77, "Multi-Currency", "Multi-Currency", "Team1"),
    MYWORK(78, "MyWork", "MyWork", "Team1"),
    NAVIGATION(79, "Navigation", "Navigation", "Team1"),
    NEWAREA(80, "New Area", "New Area", "Team1"),
    NOTIFICATIONS(81, "Notifications", "Notifications", "Team1"),
    ORMECLIPSELINK(82, "ORM (EclipseLink)", "ORM (EclipseLink)", "Team1"),
    OFFICE365(83, "Office 365", "Office 365", "Team1"),
    OLDATTASKANDROID(84, "Old AtTask Android", "Old AtTask Android", "Team1"),
    OLDATTASKIPHONE(85, "Old AtTask iPhone", "Old AtTask iPhone", "Team1"),
    ORACLE(86, "Oracle", "Oracle", "Team1"),
    ORGCHART(87, "Org Chart", "Org Chart", "Team1"),
    OTHER(88, "Other", "Other", "Team1"),
    OUTLOOKINTEGRATION(89, "Outlook Integration", "Outlook Integration", "Team1"),
    PHQREVIEWANDAPPROVALTOOL(90, "PHQ Review & approval tool",
            "PHQ Review & approval tool", "Team1"),
    PHQTABLETANDMOBILEAPPLICATIONS(91, "PHQ Tablet and mobile Applications",
            "PHQ Tablet and mobile Applications", "Team1"),
    PHQVIEWER(92, "PHQ Viewer", "PHQ Viewer", "Team1"),
    PEOPLE(93, "People", "People", "Team1"),
    PERFORMANCE(94, "Performance", "Performance", "Team1"),
    PORTFOLIOMANAGEMENT(95, "Portfolio Management", "Portfolio Management", "Team1"),
    PORTFOLIOOPTIMIZER(96, "Portfolio Optimizer", "Portfolio Optimizer", "Team1"),
    PRINTINGANDEXPORTING(97, "Printing and Exporting", "Printing and Exporting", "Team1"),
    PROGRAMMANAGEMENT(98, "Program Management", "Program Management", "Team1"),
    PROJECTMANAGEMENT(99, "Project Management", "Project Management", "Team1"),
    PROJECTUTILIZATIONREPORT(100, "Project Utilization Report", "Project Utilization Report", "Team1"),
    PROOFVIEWER(101, "ProofViewer", "ProofViewer", "Team1"),
    PROOFINGINWF(102, "Proofing in WF", "Proofing in WF", "Team1"),
    PROVISIONING(103, "Provisioning", "Provisioning", "Team1"),
    QUEUES(104, "Queues", "Queues", "Team1"),
    QUICKSEARCH(105, "Quick Search", "Quick Search", "Team1"),
    RECURRINGTASKS(106, "Recurring Tasks", "Recurring Tasks", "Team1"),
    REPORTBUILDER(107, "Report Builder", "Report Builder", "Team1"),
    REPORTDELIVERY(108, "Report Delivery", "Report Delivery", "Team1"),
    REPORTRESULTS(109, "Report Results", "Report Results", "Team1"),
    RESOURCEESTIMATES(110, "Resource Estimates", "Resource Estimates", "Team1"),
    RESOURCEGRID(111, "Resource Grid", "Resource Grid", "Team1"),
    RESOURCEMANAGEMENT(112, "Resource Management", "Resource Management", "Team1"),
    RESOURCEPLANNING(113, "Resource Planning", "Resource Planning", "Team1"),
    RESOURCESCHEDULING(114, "Resource Scheduling", "Resource Scheduling", "Team1"),
    RESOURCESWAP(115, "Resource Swap", "Resource Swap", "Team1"),
    ROLE(116, "Role", "Role", "Team1"),
    SOLR(117, "SOLR", "SOLR", "Team1"),
    SSOSAMLLDAP(118, "SSO SAML LDAP", "API", "Team1"),
    SALESFORCEINTEGRATION(119, "Salesforce Integration", "Salesforce Integration", "Team1"),
    SANDBOXPREVIEW(120, "Sandbox Preview", "Sandbox Preview", "Team1"),
    SEARCHE(121, "Search", "Search", "Team1"),
    SEARCHINDEXING(122, "Search Indexing", "Search Indexing", "Team1"),
    SECURITY(123, "Security", "Security", "Team1"),
    SERVICEORIENTEDARCHITECTURE(124, "Service Oriented Architecture",
            "Service Oriented Architecture", "Team1"),
    SLACK(125, "Slack", "Slack", "Team1"),
    SMARTASSIGNMENTS(126, "Smart Assignments", "Smart Assignments", "Team1"),
    SOFTDELETESRECYCLEBIN(127, "Soft Deletes (Recycle Bin)", "Soft Deletes (Recycle Bin)", "Team1"),
    SYLON(128, "SyLon", "SyLon", "Team1"),
    TASKDETAIL(129, "Task Detail", "Task Detail", "Team1"),
    TEAMBUILDER(130, "Team Builder", "Team Builder", "Team1"),
    TEAMHOME(131, "Team Home", "Team Home", "Team1"),
    TEAMMANAGEMENT(132, "Team Management", "Team Management", "Team1"),
    TEMPLATETASKDETAIL(133, "Template Task Detail", "Template Task Detail", "Team1"),
    TEMPLATES(134, "Templates", "Templates", "Team1"),
    TESTACCOUNTSDATA(135, "Test Accounts Data", "Test Accounts Data", "Team1"),
    TIMETRACKING(136, "Time Tracking", "Time Tracking", "Team1"),
    TIMELINECALCULATION(137, "Timeline Calculation", "Timeline Calculation", "Team1"),
    TIMELINEENGINE(138, "Timeline Engine", "Timeline Engine", "Team1"),
    TIMESHEETS(139, "Timesheets", "Timesheets", "Team1"),
    TOPNAVIGATION(140, "Top Navigation", "Top Navigation", "Team1"),
    TRANSLATION(141, "Translation", "Translation", "Team1"),
    UXUI(142, "UX UI", "UX UI", "Team1"),
    UPDATEFEEDSAUDITJOURNALENTRIES(143, "Update Feeds Audit Journal Entries",
            "Update Feeds Audit Journal Entries", "Team1"),
    UPDATESTREAM(144, "Update Stream", "Update Stream", "Team1"),
    USERDETAILS(145, "User Details", "User Details", "Team1"),
    USERINTERFACE(146, "User Interface", "User Interface", "Team1"),
    USERMANAGEMENT(147, "User Management", "User Management", "Team1"),
    USERUTILIZATION(148, "User Utilization", "User Utilization", "Team1"),
    VIEWS(149, "Views", "Views", "Team1"),
    VIEWSANDSORTING(150, "Views and Sorting", "Views and Sorting", "Team1"),
    WORDINTEGRATION(151, "Word Integration", "Word Integration", "Team1"),
    WORKFRONTDAM(152, "Workfront DAM", "Workfront DAM", "Team1"),
    WORKFRONTFORANDROID(153, "Workfront for Android", "Workfront for Android", "Team1"),
    WORKFRONTFORIPHONE(154, "Workfront for iPhone", "Workfront for iPhone", "Team1"),
    WORKINGONCALENDAR(155, "Working On Calendar", "Working On Calendar", "Team1"),
    IPAD(156, "iPad", "iPad", "Team1"),
    SETUP(157, "Setup", "Setup", "Team1");


    private long id;

    private String name;

    private String description;

    private String teamName;

    AppArea(long id, String name, String description, String teamName) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.teamName = teamName;
    }

    public static AppArea getById(long id) {
        AppArea[] appAreas = AppArea.values();
        for (AppArea appArea : appAreas) {
            if (appArea.getId() == id)
                return appArea;
        }
        return null;
    }

    public static AppArea getByName(String name) {
        AppArea[] appAreas = AppArea.values();
        for (AppArea appArea : appAreas) {
            if (appArea.getName().equals(name))
                return appArea;
        }
        return null;
    }

    public static List<AppArea> getAsList() {
        return Arrays.asList(AppArea.values());
    }

    public static boolean isEmpty(String string) {
        return string == null || string.isEmpty();
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTeamName() {
        return teamName;
    }

    public boolean isValid() {
        return !isEmpty(this.getName())
                && !isEmpty(this.getDescription())
                && !isEmpty(this.getTeamName());
    }
}
