## Relationships using annotations
Let's imagine that we have a One to Many situation in our database. Could be something like:  
> One **USER** can have MULTIPLE **REQUESTS**   

We are able to handle in this way:  

```java
@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user")
    private List<Request> requests = new ArrayList<>(); 
}

@Entity
public class Request {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
```

We undestand that a single **USER** is responsible and will have multiple **REQUESTS** (OneToMany) by saying:
```java
@OneToMany(mappedBy = "user")
private List<Request> requests = new ArrayList<>(); 
```

And we finish saying: 
```java
@ManyToOne @JoinColumn(name = "user_id", nullable = false)
private User user;
```
To show that many **REQUESTS** could be handled by a single **USER** (Many to One), and in each one of them we will store the 'user_id'.

---

