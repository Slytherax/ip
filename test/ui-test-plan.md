# UI Test Plan

## Test command

```sh
javac src/main/java/*.java && java -cp src/main/java KDB
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
