# lifethreads
An experiment in generative text. Matthew Jacobs and Christina Salvatore, 2008.

## Concept
In a technologically motivated world, our computers have become so complex, and so completely integrated into our lives, that they have become active participants in our day-to-day existence. As this relationship evolves, our interactions with our computers become more and more like our interactions with other humans. We speak to our computers, attempt to establish dialog with them, and our lives come to a crashing halt when our computers have a problem we cannot solve.

However, though we have begun to treat our computers almost as individual, sentient beings, they cannot interact with us. The computer's responses are limited by its functionality. But the computer itself knows what it is doing. Our computers keep very detailed logs of system, user, and server information. In our project, we wanted to present the computer interacting with the world as a human would by allowing it to share its daily activities through a blog.

In this way, we create a human outlet for the activities of machines which have become complex enough that these activities would otherwise be obfuscated and difficult to comprehend and make sense of. We allow the computer to engage with the user, and to become an active participant in the dialog of social networks for which it has previously served only as a tool.

## Technical Notes
This project runs as a background process on a computer. It tails the log files created by the user's activities and stores the logged activities in a data repository. When the repository contains enough information, the program uses a context-free-grammar to construct human-language sentences using this data to describe the computer's activities in response to user actions. Text-to-speech technology is used to give the computer a voice through which it shares these activities, and then the sentences are used to create diary entries for the machine, which are then posted on the "machine's" blog.

View the blog here: http://meprocessingthings.blogspot.com/
