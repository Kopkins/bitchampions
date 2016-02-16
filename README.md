# Bit Champions References

## Resources

### UML
* [UML basics](http://www.ibm.com/developerworks/rational/library/content/RationalEdge/sep04/bell/)

### git
* [Atlassian Tutorial](https://www.atlassian.com/git/tutorials/)
* [Official Docs](https://git-scm.com/documentation)
* [How to use .gitignore](https://help.github.com/articles/ignoring-files/)

### Editor Config
* [What is it?](http://editorconfig.org/)
* [Get a plugin for your Editor](http://editorconfig.org/#download)

### Swing
* [Getting Started](http://docs.oracle.com/javase/tutorial/uiswing/learn/index.html)
* [Events](https://docs.oracle.com/javase/tutorial/uiswing/events/intro.html)
* Drag and Drop
  * [Intro](http://docs.oracle.com/javase/tutorial/uiswing/dnd/intro.html)
  * [Demo](http://docs.oracle.com/javase/tutorial/uiswing/dnd/basicdemo.html)
  * [Tutorial](http://zetcode.com/tutorials/javaswingtutorial/draganddrop/)
  
# Iteration #1
## Responsbilities
| Name      | Responsibility    | Due Date  |
|---        |:---:              |---:       |
| Jesse     | Specification     | 2/9/16    |
| Jared     | Design            | 2/9/16    |
| Kyle      | Implementation    | 2/26/16   |
| Vince S.  | Tests             | 2/26/16   |
---
## Problem Statement
Users requre a tool with which they can easily and efficiently edit UML class diagrams.
## System Personel
### Descrption of Users
Users shoud be familiar with UML, and have a need for designing a class diagram.
### Description of System Developers
Our development team will consist of 5 junior Java developers
* Kyle Hopkins
* Jared McAndrews
* Jesse Platts
* Vincent Smith
* Vincent Viggiano

## Operational Settings
### Target Platforms
This program will run on various platforms throught the Java Virtual Machine (JVM)
### Required Software Environment
Mac, Windows, or Linux machines with JRE8u73 x64 installed and properly configured.
## Functional Requirements
### Functional Description
#### Feature List
* Drag and Drop class boxes
* Ability to draw lines on a canvas
* Ability to label class boxes with class name, attributes, and methods

### User Interface
#### Overview
Useres will interact primarily through mouse and keyboard. Mouse inputes will be used to process drag and drop events on elements of the diagram, as well as to draw lines. Mouse events will also be used to select regions of class boxes in which text can be recorded.
#### Menus
* Menubar
    * At this point this is primarily for easier onboarding sinceusers are accustomed to having this bar. Will house information about our project as well as an option to exit the program and start a new diagram.
* Toolbox
    *  for housing UML elements that a user may wish to use in their diagram.

#### Windows
* Primary windows for collecting user interactions and displaying the editor's view.
* Modals for displaying messages to the user.

## Non-Functional Requirements
### Reliability
The program should not crash, and it should open and close cleanly.
### Performance
Pretty good. Drag and drop should allow for smooth movement of the element across the canvas. Button press should add elements to the canvas near instantaneously.
### Usability
Should be intuitive and not cause frustration.
### Portability 
Anyone who has the `.jar` of our project and a properly configured environment as specified in section 3.2 should be able to run this program. The project should ba as portable as Java can make it.

## Future Enhancements
* Offering the ability to 'snap' elements to a grid
* Add anchors to class boxes for lines to be attached to
* Allow lines to have bends and curves in them
* Allow for saving and loading documents
* Implement deleting elements from the canvas
* Multiple arrowheads for relationships
