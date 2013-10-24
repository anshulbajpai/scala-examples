case class User(id : Int)

val users = Map((1, User(1)),(2, User(2)))

val friends = Map((1, users(2)),(2, users(1)))


trait FriendsService{
  def getUserFriends(id : Int) = friends(id)
}

trait UserService{
  def getUser(id : Int) = users(id)
}

trait UserController extends UserService with FriendsService{
  def get(id : Int) = getUser(id)
  def getFriends(id : Int) = getUserFriends(id)
}

val userController = new UserController {}

userController.get(1)
userController.getFriends(1)

trait UserServiceComponent {
  val userService : UserServiceX
  trait UserServiceX{
    def getUser(id : Int) = users(id)
  }  
}

trait FriendsServiceComponent {
  val friendsService : FriendsServiceX
  trait FriendsServiceX{
    def getUserFriends(id : Int) = friends(id)
  }  
}

trait UserControllerX {
  self : UserServiceComponent with FriendsServiceComponent =>

  def get(id : Int) = userService.getUser(id)
  def getFriends(id : Int) = friendsService.getUserFriends(id)
}

val userControllerX = new UserControllerX with UserServiceComponent with FriendsServiceComponent{
  val userService = new UserServiceX {}
  val friendsService = new FriendsServiceX {}
}

//val userControllerX = new UserControllerX with UserServiceComponent with FriendsServiceComponent{
//  val userService = mock[UserServiceX]
//  val friendsService = mock[FriendsServiceX]
//}


userControllerX.get(1)
userControllerX.getFriends(1)

