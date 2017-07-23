INSERT INTO work_flow.user (first_name, last_name, email, passcode, avatar_url, rating) VALUES ('John', 'Smith', 'john@gmail.com', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb', '/images/default/user-avatar.png', 15);
INSERT INTO work_flow.user (first_name, last_name, email, passcode, avatar_url, rating) VALUES ('James', 'Gosling', 'jg@gmail.com', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb', '/images/default/user-avatar.png', 13);
INSERT INTO work_flow.user (first_name, last_name, email, passcode, avatar_url, rating) VALUES ('Bill', 'Gates', 'bg@gmail.com', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb', '/images/default/user-avatar.png', -7);
INSERT INTO work_flow.user (first_name, last_name, email, passcode, avatar_url, rating) VALUES ('Steve', 'Jobes', 'sj@gmail.com', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb', '/images/default/user-avatar.png', 13);
INSERT INTO work_flow.user (first_name, last_name, email, passcode, avatar_url, rating) VALUES ('Steve', 'Wozniak', 'sw@gmail.com', 'ca978112ca1bbdcafac231b39a23dc4da786eff8147c4e72b9807785afee48bb', '/images/default/user-avatar.png', 10);


INSERT INTO work_flow.post (post_id, user_id, post_time, title, content, apparea_id) VALUES (null, 2, '2017-07-21 17:30:58', 'Having multiple sessionFactory instances', 'I am porting a legacy application to hibernate 5 and Im having trouble with the login phase. Heres how it works (I cant change that):
user initially connects to oracle DB with a generic login/password (same for all users)
then user runs a "login" stored procedure and enters a unique password as parameter
the procedure returns a specific Oracle DB username/password to the user
user disconnects from DB and reconnects using the credentials given by the stored procedure
I currently create one instance of sessionFactory per connected user, but Im worried that this will impact performance. Is there a better way to do this?

Thanks.', 86);
INSERT INTO work_flow.post (post_id, user_id, post_time, title, content, apparea_id) VALUES (1, 3, '2017-07-21 23:32:26', 'Having multiple sessionFactory instances', 'Hibernate Multitenancy with "Separate database" strategy would work even if you are actually
    connecting to the same database but different credentials.
    MultiTenantConnectionProvider must be specified to return connection with right username and password.', 86);
INSERT INTO work_flow.post (post_id, user_id, post_time, title, content, apparea_id) VALUES (null, 4, '2017-07-21 17:34:19', 'Do you use UML in Agile development practices?

', 'It feels like an artifacts of an earlier days, but UML sure does have its use. However, agile processes like Extreme Programming advocates "embracing changes", does that also means I should make less documents and UML models as well? Since they gives the impression of setting something in stone.

Where does UML belongs in an Agile development practice? Other than the preliminary spec documents, should I use it at all?', 4);
INSERT INTO work_flow.post (post_id, user_id, post_time, title, content, apparea_id) VALUES (3, 3, '2017-07-21 18:42:31', 'Do you use UML in Agile development practices?

', 'Breeze through Robert Martin''s Agile Principles, Patterns and Practices

The suggestion is to use UML to communicate designs within the team.. a shared language ; anyone taking a look at the diagram can understand the solution (faster than talking about it) and contribute quicker.
If you find the team making the same diagrams over and over again, someone will make a good version and store it on the wiki / source control. Overtime the more useful diagrams will start to collate in that place.
Don''t spend too much time on it... you don''t need too much detail. Models are built in the architectural / construction realms because building a house to validate-test the design is expensive/infeasible. Software is not like that - you could validate your design by just coding it up in the time you make a UML model of your untested design (with all the bells and whistles).
So says UncleBob... I concur.', 4);
INSERT INTO work_flow.post (post_id, user_id, post_time, title, content, apparea_id) VALUES (null, 3, '2017-07-23 20:54:42', 'Dilemma: when to use Fragments vs Activities:', 'I know that Activities are designed to represent a single screen of my application, while Fragments are designed to be reusable UI layouts with logic embedded inside of them.

Until not long ago, I developed an application as it said that they should be developed. I created an Activity to represent a screen of my application and used Fragments for ViewPager or Google Maps. I rarely created a ListFragment or other UI that can be reused several times.

Recently I stumbled on a project that contains only 2 Activities one is a SettingsActivity and other one is the MainActivity. The layout of the MainActivity is populated with many hidden full screen UI fragments and only one is shown. In the Acitivty logic there are many FragmentTransitions between the different screens of the application.

What I liked about this approach is that because the application uses an ActionBar, it stays intact and does not move with the screen switching animation, which is what happens with Activity switching. This give a more fluent feel to those screen transitions.

So I guess what I''m asking is to share your current development manner regarding this topic, I know it might look like an opinion based question at first look but I look at it as an Android design and architecture question... Not really an opinion based one.

UPDATE (01.05.2014): Following this presentation by Eric Burke from Square, (which I have to say is a great presentation with a lot of useful tools for android developers. And I am not related in any way to Square)', 142);
INSERT INTO work_flow.post (post_id, user_id, post_time, title, content, apparea_id) VALUES (null, 1, '2017-07-23 21:04:52', 'Why would I consider using an RTOS for my embedded project?', 'First the background, specifics of my question will follow:

At the company that I work at the platform we work on is currently the Microchip PIC32 family using the MPLAB IDE as our development environment. Previously we''ve also written firmware for the Microchip dsPIC and TI MSP families for this same application. The firmware is pretty straightforward in that the code is split into three main modules: device control, data sampling, and user communication (usually a user PC). Device control is achieved via some combination of GPIO bus lines and at least one part needing SPI or I2C control. Data sampling is interrupt driven using a Timer module to maintain sample frequency and more SPI/I2C and GPIO bus lines to control the sampling hardware (ie. ADC). User communication is currently implemented via USB using the Microchip App Framework.', 71);
INSERT INTO work_flow.post (post_id, user_id, post_time, title, content, apparea_id) VALUES (null, 5, '2017-07-23 21:13:53', 'Embedded Application Server

', 'I''m planning to do a C++ standalone desktop application which uses web / html as the main format of it''s documents content.

I need suggestions for a good application server that can generate dynamic contents and can be easily embedded in my desktop application. It doesn''t matter what server side programming languages it supports.', 6);
INSERT INTO work_flow.post (post_id, user_id, post_time, title, content, apparea_id) VALUES (3, 2, '2017-07-21 23:37:54', 'Do you use UML in Agile development practices?

', 'Use what you find helpful. If a UML diagram helps you visualize what you need to do, use it. If a whiteboard does the same thing, use that.

Just don''t make UML diagrams for the sake of making UML diagrams. Our time is too expensive to spend doing useless things.', 4);
INSERT INTO work_flow.post (post_id, user_id, post_time, title, content, apparea_id) VALUES (3, 1, '2017-07-23 21:40:14', 'Do you use UML in Agile development practices?

', 'For true agility you should express your design once and only once -- in code.

So while UML diagrams might well be useful as a shorthand notation for communication while preparing a task, and can usefully summarize a design while discussing designs with colleagues, they shouldn''t be a delivered artifact.

In particular, they shouldn''t be maintained, as this is needless duplication.', 4);
INSERT INTO work_flow.post (post_id, user_id, post_time, title, content, apparea_id) VALUES (5, 1, '2017-07-23 20:56:58', 'Dilemma: when to use Fragments vs Activities:', 'Experts will tell you: "When I see the UI, I will know whether to use an Activity or a Fragment". In the beginning this will not have any sense, but in time, you will actually be able to tell if you need Fragment or not.

There is a good practice I found very helpful for me. It occurred to me while I was trying to explain something to my daughter.

Namely, imagine a box which represents a screen. Can you load another screen in this box? If you use a new box, will you have to copy multiple items from the 1st box? If the answer is Yes, then you should use Fragments, because the root Activity can hold all duplicated elements to save you time in creating them, and you can simply replace parts of the box.

But don''t forget that you always need a box container (Activity) or your parts will be dispersed. So one box with parts inside.

Take care not to misuse the box. Android UX experts advise (you can find them on YouTube) when we should explicitly load another Activity, instead to use a Fragment (like when we deal with the Navigation Drawer which has categories). Once you feel comfortable with Fragments, you can watch all their videos. Even more they are mandatory material.

Can you right now look at your UI and figure out if you need an Activity or a Fragment? Did you get a new perspective? I think you did.', 142);
INSERT INTO work_flow.post (post_id, user_id, post_time, title, content, apparea_id) VALUES (5, 2, '2017-07-23 20:58:21', 'Dilemma: when to use Fragments vs Activities:', 'My philosophy is this:

Create an Activity only if it''s absolutely absolutely required. With the backstack made available for committing bunch of fragment transactions, I try to create as minimum of Activities in my app as possible. Also, communicating between various fragments is much easier rather than sending data back and forth between activities.

Activity transitions are expensive, right? At least I believe so - since the old activity as to be destroyed/paused/stopped, pushed onto the stack and then the new activity has to be created/started/resumed.

It''s just my philosophy since fragments were introduced.', 142);
INSERT INTO work_flow.post (post_id, user_id, post_time, title, content, apparea_id) VALUES (5, 4, '2017-07-23 20:59:08', 'Dilemma: when to use Fragments vs Activities:', 'Why I prefer Fragment over Activity in ALL CASES.

Activity is expensive. In Fragment, views and property states are separated - whenever a fragment is in backstack, its views will be destroyed. So you can stack much more Fragments than Activity.
Backstack manipulation. With FragmentManager, it''s easy to clear all the Fragments, insert more than on Fragments and etcs. But for Activity, it will be a nightmare to manipulate those stuff.
A much predictable lifecycle. As long as the host Activity is not recycled. the Fragments in the backstack will not be recycled. So it''s possible to use FragmentManager::getFragments() to find specific Fragment (not encouraged).', 142);
INSERT INTO work_flow.post (post_id, user_id, post_time, title, content, apparea_id) VALUES (5, 5, '2017-07-23 21:00:19', 'Dilemma: when to use Fragments vs Activities:', 'There''s more to this than you realize, you have to remember than an activity that is launched does not implicitly destroy the calling activity. Sure, you can set it up such that your user clicks a button to go to a page, you start that page''s activity and destroy the current one. This causes a lot of overhead. The best guide I can give you is:

** Start a new activity only if it makes sense to have the main activity and this one open at the same time (think of multiple windows).

A great example of when it makes sense to have multiple activities is Google Drive. The main activity provides a file explorer. When a file is opened, a new activity is launched to view that file. You can press the recent apps button which will allow you to go back to the browser without closing the opened document, then perhaps even open another document in parallel to the first.', 142);
INSERT INTO work_flow.post (post_id, user_id, post_time, title, content, apparea_id) VALUES (5, 4, '2017-07-23 21:01:33', 'Dilemma: when to use Fragments vs Activities:', 'Thing I did: Using less fragment when possible. Unfortunately, it''s possible in almost case. So, I end up with a lot of fragments and a little of activities. Some drawbacks I''ve realized:

ActionBar & Menu: When 2 fragment has different title, menu, that
will hard to handle. Ex: when adding new fragment, you can change action bar title, but when pop it from backstack there is no way to restore the old title. You may need an Toolbar in every fragment for this case, but let believe me, that will spend you more time.
When we need startForResult, activity has but fragment hasn''t.
Don''t have transition animation by default
My solution for this is using an Activity to wrap a fragment inside. So we have separate action bar, menu, startActivityForResult, animation,...', 142);
INSERT INTO work_flow.post (post_id, user_id, post_time, title, content, apparea_id) VALUES (6, 2, '2017-07-23 21:05:58', 'Why would I consider using an RTOS for my embedded project?', 'There are many many reasons you might want to use an RTOS. They are varied & the degree to which they apply to your situation is hard to say. (Note: I tend to think this way: RTOS implies hard real time which implies preemptive kernel...)

Rate Monotonic Analysis (RMA) - if you want to use Rate Monotonic Analysis to ensure your timing deadlines will be met, you must use a pre-emptive scheduler
Meet real-time deadlines - even without using RMA, with a priority-based pre-emptive RTOS, your scheduler can help ensure deadlines are met. Paradoxically, an RTOS will typically increase interrupt latency due to critical sections in the kernel where interrupts are usually masked
Manage complexity -- definitely, an RTOS (or most OS flavors) can help with this. By allowing the project to be decomposed into independent threads or processes, and using OS services such as message queues, mutexes, semaphores, event flags, etc. to communicate & synchronize, your project (in my experience & opinion) becomes more manageable. I tend to work on larger projects, where most people understand the concept of protecting shared resources, so a lot of the rookie mistakes don''t happen. But beware, once you go to a multi-threaded approach, things can become more complex until you wrap your head around the issues.
Use of 3rd-party packages - many RTOSs offer other software components, such as protocol stacks, file systems, device drivers, GUI packages, bootloaders, and other middleware that help you build an application faster by becoming almost more of an "integrator" than a DIY shop.
Testing - yes, definitely, you can think of each thread of control as a testable component with a well-defined interface, especially if a consistent approach is used (such as always blocking in a single place on a message queue). Of course, this is not a substitute for unit, integration, system, etc. testing.
Robustness / fault tolerance - an RTOS may also provide support for the processor''s MMU (in your PIC case, I don''t think that applies). This allows each thread (or process) to run in its own protected space; threads / processes cannot "dip into" each others'' memory and stomp on it. Even device regions (MMIO) might be off limits to some (or all) threads. Strictly speaking, you don''t need an RTOS to exploit a processor''s MMU (or MPU), but the 2 work very well hand-in-hand.
Generally, when I can develop with an RTOS (or some type of preemptive multi-tasker), the result tends to be cleaner, more modular, more well-behaved and more maintainable. When I have the option, I use one.

Be aware that multi-threaded development has a bit of a learning curve. If you''re new to RTOS/multithreaded development, you might be interested in some articles on Choosing an RTOS, The Perils of Preemption and An Introduction to Preemptive Multitasking.

Lastly, even though you didn''t ask for recommendations... In addition to the many numerous commercial RTOSs, there are free offerings (FreeRTOS being one of the most popular), and the Quantum Platform is an event-driven framework based on the concept of active objects which includes a preemptive kernel. There are plenty of choices, but I''ve found that having the source code (even if the RTOS isn''t free) is advantageous, esp. when debugging.', 71);
INSERT INTO work_flow.post (post_id, user_id, post_time, title, content, apparea_id) VALUES (6, 3, '2017-07-23 21:06:36', 'Why would I consider using an RTOS for my embedded project?', 'Code re-use -- if you code drivers/protocol-handlers using an RTOS API they may plug into future projects easier

Debugging -- some IDEs (such as IAR Embedded Workbench) have plugins that show nice live data about your running process such as task CPU utilization and stack utilization', 71);
INSERT INTO work_flow.post (post_id, user_id, post_time, title, content, apparea_id) VALUES (6, 4, '2017-07-23 21:07:37', 'Why would I consider using an RTOS for my embedded project?', 'RTOS, first and foremost permits you to organize your parallel flows into the set of tasks with well-defined synchronization between them.

IMO, the non-RTOS design is suitable only for the single-flow architecture where all your program is one big endless loop. If you need the multi-flow - a number of tasks, running in parallel - you''re better with RTOS. Without RTOS you''ll be forced to implement this functionality in-house, re-inventing the wheel.', 71);
INSERT INTO work_flow.post (post_id, user_id, post_time, title, content, apparea_id) VALUES (7, 3, '2017-07-23 21:15:07', 'Embedded Application Server

', 'I''m assuming that your application will be used through a web-browser interface, which is the reason why you are generating HTML content. With that in mind, you can still use C/C++ for your both the front and back end logic through a web-server.

There are several small & fast web servers that support CGI services. You can then write your back-end servers using C/C++ as CGI services. You can also write your client using C/C++ with an embedded browser like Gecko or Webkit within it.', 6);
INSERT INTO work_flow.post (post_id, user_id, post_time, title, content, apparea_id) VALUES (null, 3, '2017-07-23 21:18:04', 'Android application and Application server

', 'I want to develop a basic form application which records datas into a sql server. However, it is commercial work so i need to use an application server for security reasons. I searched and discovered that android can connect to a sql server via a open source library but i''m not sure how to use an application server with these?', 6);


INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 1);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 1);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 1);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 1);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 2);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 2);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 2);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 2);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 3);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 3);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 3);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 3);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 4);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 4);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 4);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 4);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 5);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 5);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 5);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 5);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 6);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 6);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 6);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 6);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 7);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 7);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 7);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 7);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 8);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 8);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 8);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 8);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 9);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 9);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 9);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 9);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 10);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 10);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 10);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 10);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 10);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 11);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 11);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 11);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 11);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 11);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 12);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 12);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 12);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 12);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 12);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 13);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 13);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 13);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 13);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 13);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 14);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 14);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 14);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 14);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 15);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 15);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 15);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 15);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 15);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 16);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 16);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 16);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 16);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 16);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 17);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 17);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 17);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 17);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 17);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 18);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 18);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 18);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 18);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 19);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 19);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 19);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 19);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 20);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 20);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 20);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 20);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 21);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 21);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 21);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 21);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 21);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 22);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 22);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 22);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 22);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 22);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 23);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 23);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 23);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 23);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 23);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 24);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 24);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 24);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 24);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 24);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 25);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 25);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 25);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 25);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 25);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 26);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 26);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 26);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 26);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 26);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 27);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 27);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 27);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 27);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 27);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 28);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 28);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 28);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 28);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 28);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 29);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 29);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 29);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 29);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 29);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 30);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 30);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 30);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 30);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 30);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 31);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 31);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 31);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 31);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 31);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 32);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 32);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 32);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 32);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 32);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 33);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 33);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 33);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 33);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 33);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 34);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 34);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 34);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 34);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 34);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 35);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 35);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 35);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 35);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 35);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 36);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 36);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 36);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 36);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 36);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 37);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 37);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 37);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 37);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 37);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 38);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 38);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 38);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 38);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 38);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 39);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 39);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 39);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 39);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 39);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 40);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 40);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 40);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 40);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 40);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 41);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 41);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 41);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 41);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 41);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 42);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 42);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 42);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 42);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 42);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 43);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 43);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 43);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 43);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 43);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 44);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 44);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 44);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 44);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 44);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 45);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 45);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 45);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 45);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 45);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 46);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 46);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 46);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 46);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 46);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 47);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 47);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 47);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 47);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 47);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 48);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 48);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 48);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 48);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 48);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 49);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 49);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 49);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 49);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 49);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 50);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 50);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 50);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 50);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 50);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 51);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 51);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 51);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 51);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 51);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 52);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 52);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 52);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 52);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 52);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 53);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 53);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 53);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 53);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 53);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 54);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 54);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 54);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 54);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 54);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 55);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 55);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 55);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 55);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 55);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 56);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 56);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 56);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 56);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 56);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 57);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 57);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 57);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 57);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 57);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 58);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 58);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 58);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 58);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 58);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 59);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 59);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 59);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 59);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 59);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 60);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 60);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 60);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 60);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 60);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 61);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 61);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 61);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 61);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 61);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 62);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 62);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 62);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 62);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 62);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 63);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 63);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 63);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 63);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 63);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 64);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 64);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 64);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 64);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 64);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 65);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 65);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 65);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 65);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 65);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 66);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 66);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 66);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 66);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 66);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 67);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 67);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 67);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 67);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 67);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 68);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 68);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 68);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 68);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 68);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 69);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 69);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 69);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 69);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 69);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 70);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 70);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 70);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 70);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 70);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 71);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 71);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 71);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 71);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 71);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 72);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 72);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 72);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 72);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 72);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 73);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 73);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 73);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 73);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 73);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 74);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 74);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 74);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 74);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 74);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 75);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 75);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 75);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 75);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 75);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 76);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 76);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 76);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 76);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 76);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 77);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 77);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 77);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 77);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 77);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 78);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 78);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 78);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 78);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 78);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 79);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 79);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 79);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 79);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 79);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 80);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 80);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 80);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 80);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 80);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 81);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 81);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 81);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 81);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 81);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 82);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 82);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 82);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 82);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 82);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 83);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 83);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 83);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 83);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 83);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 84);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 84);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 84);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 84);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 84);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 85);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 85);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 85);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 85);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 85);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 86);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 86);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 86);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 86);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 86);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 87);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 87);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 87);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 87);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 87);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 88);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 88);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 88);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 88);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 88);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 89);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 89);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 89);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 89);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 89);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 90);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 90);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 90);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 90);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 90);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 91);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 91);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 91);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 91);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 91);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 92);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 92);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 92);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 92);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 92);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 93);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 93);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 93);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 93);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 93);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 94);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 94);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 94);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 94);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 94);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 95);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 95);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 95);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 95);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 95);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 96);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 96);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 96);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 96);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 96);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 97);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 97);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 97);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 97);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 97);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 98);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 98);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 98);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 98);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 98);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 99);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 99);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 99);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 99);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 99);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 100);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 100);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 100);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 100);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 100);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 101);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 101);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 101);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 101);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 101);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 102);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 102);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 102);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 102);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 102);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 103);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 103);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 103);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 103);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 103);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 104);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 104);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 104);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 104);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 104);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 105);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 105);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 105);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 105);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 105);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 106);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 106);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 106);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 106);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 106);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 107);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 107);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 107);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 107);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 107);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 108);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 108);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 108);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 108);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 108);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 109);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 109);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 109);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 109);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 109);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 110);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 110);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 110);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 110);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 110);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 111);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 111);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 111);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 111);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 111);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 112);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 112);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 112);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 112);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 112);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 113);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 113);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 113);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 113);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 113);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 114);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 114);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 114);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 114);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 114);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 115);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 115);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 115);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 115);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 115);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 116);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 116);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 116);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 116);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 116);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 117);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 117);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 117);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 117);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 117);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 118);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 118);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 118);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 118);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 118);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 119);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 119);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 119);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 119);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 119);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 120);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 120);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 120);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 120);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 120);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 121);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 121);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 121);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 121);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 121);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 122);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 122);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 122);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 122);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 122);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 123);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 123);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 123);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 123);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 123);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 124);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 124);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 124);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 124);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 124);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 125);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 125);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 125);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 125);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 125);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 126);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 126);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 126);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 126);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 126);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 127);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 127);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 127);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 127);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 127);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 128);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 128);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 128);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 128);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 128);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 129);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 129);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 129);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 129);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 129);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 130);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 130);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 130);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 130);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 130);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 131);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 131);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 131);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 131);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 131);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 132);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 132);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 132);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 132);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 132);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 133);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 133);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 133);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 133);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 133);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 134);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 134);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 134);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 134);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 134);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 135);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 135);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 135);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 135);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 135);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 136);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 136);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 136);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 136);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 136);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 137);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 137);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 137);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 137);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 137);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 138);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 138);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 138);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 138);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 138);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 139);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 139);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 139);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 139);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 139);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 140);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 140);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 140);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 140);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 140);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 141);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 141);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 141);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 141);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 141);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 142);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 142);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 142);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 142);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 142);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 143);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 143);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 143);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 143);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 143);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 144);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 144);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 144);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 144);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 144);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 145);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 145);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 145);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 145);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 145);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 146);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 146);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 146);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 146);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 146);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 147);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 147);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 147);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 147);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 147);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 148);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 148);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 148);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 148);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 148);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 149);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 149);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 149);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 149);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 149);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 150);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 150);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 150);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 150);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 150);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 151);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 151);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 151);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 151);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 151);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 152);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 152);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 152);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 152);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 152);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 153);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 153);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 153);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 153);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 153);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 154);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 154);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 154);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 154);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 154);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 155);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 155);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 155);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 155);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 155);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 156);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 156);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 156);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 156);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (1, 157);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (2, 157);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (3, 157);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (4, 157);
INSERT INTO work_flow.user_apparea (user_id, apparea_id) VALUES (5, 157);


INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (1, 1);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (2, 1);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (3, 1);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (4, 1);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (5, 1);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (2, 2);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (3, 2);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (5, 2);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (1, 3);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (1, 4);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (1, 5);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (4, 5);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (2, 6);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (3, 6);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (4, 6);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (1, 8);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (3, 8);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (4, 8);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (3, 9);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (4, 9);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (1, 10);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (1, 11);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (2, 11);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (2, 13);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (4, 13);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (1, 14);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (2, 14);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (1, 15);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (2, 15);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (3, 15);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (4, 15);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (4, 16);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (2, 17);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (3, 17);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (2, 18);
INSERT INTO work_flow.user_post_likes (user_id, post_id) VALUES (3, 18);


INSERT INTO work_flow.user_post_dislikes (user_id, post_id) VALUES (1, 2);
INSERT INTO work_flow.user_post_dislikes (user_id, post_id) VALUES (3, 3);
INSERT INTO work_flow.user_post_dislikes (user_id, post_id) VALUES (3, 4);
INSERT INTO work_flow.user_post_dislikes (user_id, post_id) VALUES (4, 4);
INSERT INTO work_flow.user_post_dislikes (user_id, post_id) VALUES (1, 7);
INSERT INTO work_flow.user_post_dislikes (user_id, post_id) VALUES (3, 8);
INSERT INTO work_flow.user_post_dislikes (user_id, post_id) VALUES (1, 9);
INSERT INTO work_flow.user_post_dislikes (user_id, post_id) VALUES (1, 13);
INSERT INTO work_flow.user_post_dislikes (user_id, post_id) VALUES (4, 14);
INSERT INTO work_flow.user_post_dislikes (user_id, post_id) VALUES (1, 16);
INSERT INTO work_flow.user_post_dislikes (user_id, post_id) VALUES (4, 17);
INSERT INTO work_flow.user_post_dislikes (user_id, post_id) VALUES (5, 18);
INSERT INTO work_flow.user_post_dislikes (user_id, post_id) VALUES (1, 34);

INSERT INTO work_flow.best_answer (post_id, answer_id) VALUES (3, 8);
INSERT INTO work_flow.best_answer (post_id, answer_id) VALUES (6, 15);