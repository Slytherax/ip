# UI Test Session

**Test command:** `rm -f data/tasks.txt && rm -rf out && javac -d out src/main/java/kdb/*.java && java -cp out kdb.KDB`

## 1. Example: exits on bye

**Aim:** Confirm that the chatbot accepts the exit command and prints its farewell.

### Console input

```text
bye
```

### Console output


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

**Result:** PASS

## 2. Saves the changed task list

**Aim:** Confirm that adding and deleting a task completes successfully and triggers the write-only persistence path.

### Console input

```text
todo buy milk
delete 1
bye
```

### Console output


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

**Result:** PASS
