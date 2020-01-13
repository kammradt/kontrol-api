## Relationships using annotations
Let's imagine that we have a One to Many situation in our database. Could be something like:  
> One **USER** can have MULTIPLE **REQUESTS**   

We are able to handle in this way:  

```java
@Entity
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @OnDelete(action = OnDeleteAction.CASCADE)
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
@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
private List<Request> requests = new ArrayList<>(); 
```

And we finish saying: 
```java
@ManyToOne @JoinColumn(name = "user_id", nullable = false)
private User user;
```
To show that many **REQUESTS** could be handled by a single **USER** (Many to One), and in each one of them we will store the 'user_id'.

---

## HTTP Status Codes 
When talking about REST APIs, it is a good ideia to keep up with correct status codes to keep our user understanding what is going on. We can achieve this by following some rules:


| CODE 	| MEANING                               	|
|------	|---------------------------------------	|
|  200 	| OK, this is a success                 	|
|  201 	| CREATED a resource                    	|
|  400 	| BAD REQUEST, verify the body/params   	|
|  401 	| UNAUTHORIZED, give a me correct token 	|
|  403 	| FORBIDDEN, you should not ask again   	|
|  404 	| NOT FOUND, try another resource       	|

---

## Differences between @NotNull and @NotBlank
This topic was a little bit confuse for me when I was creating the Bean Validations for a DTO class, so now that I undestood how it works, I will write here too 😀

> Lets imagine the class **UserDTO** that have `name` property   

```java
public class User {

    @NotNull(message = "Name is required")
    @NotBlank(message = "Insert a valid name")
    // Which one is the better to use in this case?
    private String name;

}
```

### How it will work? 
**@NotNull**
Will give an error message if the given value for `name` is `null`.   
**BUT** it will allow the an input like `""`,  because this is not null and it do not care about empty strings. 

   
**@NotBlank**  
Will give an error message if the given value is `null` or the length is equals to zero, such as `"" or " ".trim()`   
This is a more complete validation that covers the case of `""` or `"  "`, so it is a good practice use this in **mandatory/important** fields.

For me, makes sense using *@NotBlank* most of the time.

## Using EntityListeners for custom triggers
