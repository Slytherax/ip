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

## 3. Finds matching tasks

**Aim:** Confirm that `find` searches task descriptions without regard to letter case.

### Console input

```text
todo read book
deadline return book /by 2/12/2019 1800
find BOOK
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

**Result:** PASS

## 4. Rejects find without a keyword

**Aim:** Confirm that `find` reports an error when no search keyword is provided.

### Console input

```text
find
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
Please provide a keyword to find.
____________________________________________________________
____________________________________________________________
Bye. Hope to see you again soon!
____________________________________________________________
```

**Result:** PASS
