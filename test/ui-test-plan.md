# UI Test Plan

## Test command

```sh
rm -f data/tasks.txt && rm -rf out && javac -d out src/main/java/kdb/*.java && java -cp out kdb.KDB
```

## Test cases

Add one third-level section for each UI test. Every test case must include an
aim, its complete console input, and the complete expected console output.

### Example: exits on bye

**Aim:** Confirm that the chatbot accepts the exit command and prints its farewell.

**Input:**

```text
bye
```

**Expected output:**

```text
____________________________________________________________
mm   mm   mmmmmm    mmmmmmm
##  ##    ##    ##  ##    ##
##m##     ##    ##  ##    ##
#####     ##    ##  #######
##  ##m   ##    ##  ##    ##
##   ##m  ##mmm##   ##mmmm##
Hello! I'm KDB.
What can I do for you?
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

### Saves the changed task list

**Aim:** Confirm that adding and deleting a task completes successfully and triggers the write-only persistence path.

**Input:**

```text
todo buy milk
delete 1
bye
```

**Expected output:**

```text
____________________________________________________________
mm   mm   mmmmmm    mmmmmmm
##  ##    ##    ##  ##    ##
##m##     ##    ##  ##    ##
#####     ##    ##  #######
##  ##m   ##    ##  ##    ##
##   ##m  ##mmm##   ##mmmm##
Hello! I'm KDB.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] buy milk
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Noted. I've removed this task:
  [T][ ] buy milk
Now you have 0 tasks in the list.
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

## Executable JAR test

**Aim:** Confirm that the packaged application can run from an otherwise empty
folder and can save task data beside the JAR file.

**Preparation:**

From the project root, build the JAR:

```text
./gradlew clean shadowJar
```

Create an empty test folder, copy `build/libs/duke.jar` into it, and open a
terminal in that folder.

**Input:**

```text
todo packaged task
list
bye
```

**Command:**

```text
java -jar "duke.jar"
```

**Expected result:**

```text
The chatbot starts successfully, accepts all three commands, displays
"packaged task" in the task list, and creates data/tasks.txt beside the JAR.
```

### Finds matching tasks

**Aim:** Confirm that `find` searches task descriptions without regard to letter case.

**Input:**

```text
todo read book
deadline return book /by 2/12/2019 1800
find BOOK
bye
```

**Expected output:**

```text
____________________________________________________________
mm   mm   mmmmmm    mmmmmmm
##  ##    ##    ##  ##    ##
##m##     ##    ##  ##    ##
#####     ##    ##  #######
##  ##m   ##    ##  ##    ##
##   ##m  ##mmm##   ##mmmm##
Hello! I'm KDB.
What can I do for you?
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [T][ ] read book
Now you have 1 tasks in the list.
____________________________________________________________
____________________________________________________________
Got it. I've added this task:
  [D][ ] return book (by: Dec 02 2019, 6:00 pm)
Now you have 2 tasks in the list.
____________________________________________________________
____________________________________________________________
Here are the matching tasks in your list:
1.[T][ ] read book
2.[D][ ] return book (by: Dec 02 2019, 6:00 pm)
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

### Rejects find without a keyword

**Aim:** Confirm that `find` reports an error when no search keyword is provided.

**Input:**

```text
find
bye
```

**Expected output:**

```text
____________________________________________________________
mm   mm   mmmmmm    mmmmmmm
##  ##    ##    ##  ##    ##
##m##     ##    ##  ##    ##
#####     ##    ##  #######
##  ##m   ##    ##  ##    ##
##   ##m  ##mmm##   ##mmmm##
Hello! I'm KDB.
What can I do for you?
____________________________________________________________
____________________________________________________________
Please provide a keyword to find.
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```
