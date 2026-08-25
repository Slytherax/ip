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
