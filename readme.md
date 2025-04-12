# 🏆 Match Scoreboard

A simple Java-based scoreboard to track and update match scores, with the ability to sort matches based on the 
total score and the most recently started match in case of ties.

## 📋 Features

- ⚽ **Start and update matches** with two teams and scores.
- 📊 **Sort matches**:
    - By total score - highest first.
    - By date and time - matches with the same score - most recent are listed first.

## 📜 Guidelines
1. **Read and familiarize yourself with the guidelines below.**

2. **Follow the guidelines to implement the task as per requirements described further down in this document.**

- **Keep it simple**: Follow the requirements and try to implement the simplest solution you can think of that 
works. Do not forget about edge cases!

- **Use an in-memory store solution**: (for example, just use collections to store the information you might require).

- **We don’t expect the solution to be a REST API, command line application, a Web Service, or Microservice**: 
Just a simple library implementation.

- **Focus on Quality**: Use Test-Driven Development (TDD), pay attention to OO design, Clean Code and adherence 
to SOLID principles.

- **Approach**: Code the solution according to your standards. Please share your solution with a link to a 
source control repository (e.g., GitHub, GitLab, BitBucket) as we would like you to see your progress 
(your commit history is important).

- **Add a README.md file**: Where besides the project documentation, you can make notes of any assumptions or 
things you would like to mention to us about your solution.


## 📝 Task Requirements

You are working in a sports data company, and we would like you to develop a new **Live Football World 
Cup Scoreboard library** that shows all the ongoing matches and their scores.

The scoreboard supports the following operations:

1. **Start a new match**: Assuming initial score **0 – 0** and adding it to the scoreboard. This should 
capture the following parameters:
    - 🏠 **Home team**
    - ✈️ **Away team**

2. **Update score**: This should receive a pair of absolute scores: **home team score** and **away team score**.

3. **Finish match currently in progress**: This removes a match from the scoreboard.

4. **Get a summary of matches in progress**: The matches should be ordered by their **total score**. 
The matches with the same total score will be returned ordered by the **most recently started match** 
in the scoreboard.

5. For example, if the following matches are started in the **specified order** and their scores are 
updated as follows:
     ```
     - Mexico 0 – Canada 5
     - Spain 10 – Brazil 2
     - Germany 2 – France 2
     - Uruguay 6 – Italy 6
     - Argentina 3 – Australia 1
     ```

6. The summary should be as follows:
    ```
    1. Uruguay 6 – Italy 6
    2. Spain 10 – Brazil 2
    3. Mexico 0 – Canada 5
    4. Argentina 3 – Australia 1
    5. Germany 2 – France 2
    ```

## 🔎 Details
In order to make testing neat, clean and understandable the **Match Constructor** is using given 
LocalDateTime **matchDateAndTime** parameter.
   ```java  
    public Match(String homeTeam, String awayTeam, LocalDateTime matchDateAndTime) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.homeScore = 0;
        this.awayScore = 0;
        this.startDateAndTime = matchDateAndTime;
    }
   ```

Alternatively, the constructor could use **LocalDateTime.now()** directly:
   ```java  
    public Match(String homeTeam, String awayTeam) {
    this.homeTeam = homeTeam;
    this.awayTeam = awayTeam;
    this.homeScore = 0;
    this.awayScore = 0;
    this.startDateAndTime = LocalDateTime.now();
}
   ```
However, using `LocalDateTime.now()` would require introducing a small delay, for example, `Thread.sleep(10)`, 
in the tests to ensure that there is enough time difference between the order of the matches. This could lead 
to unnecessary instability in tests and is avoided by explicitly passing **matchDateAndTime** as a parameter 
in the beformentioned constructor.


## 🛠 Prerequisites

- Java 21
- Maven