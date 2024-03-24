import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/*
* 入口类
* */
public class Main {
    public static void main(String[] args) {
        ArrayList<Account> accounts =new ArrayList<>();
        Scanner sc=new Scanner(System.in);
        while(true){
            System.out.println("==========欢迎来到ATM系统==========");
            System.out.println("1,账户登录");
            System.out.println("2,账户开户");
            System.out.println("3.退出系统");
            System.out.println("请您选择操作：");
            int x=sc.nextInt();
            switch (x){
                case 1://用户登录
                    login(accounts,sc);
                    break;
                case 2://用户开户
                    register(accounts,sc);
                    break;
                case 3:
                    System.out.println("已退出");
                    return;
                default:
                    System.out.println("您输入的命令不存在~~");
            }
        }
    }
    private static void login(ArrayList<Account> accounts,Scanner sc){
        System.out.println("==========系统开户操作==========");
        if(accounts.isEmpty()){
            System.out.println("对不起，当前系统中，无任何账户，请先开户，再来登录~~");
            return;
        }
        //进入登录操作
        while(true){
            System.out.println("请输入您的卡号：");
            String cardId=sc.next();
            Account acc=getAccountByCardId(cardId,accounts);
            if(acc!=null){
                while(true){
                    System.out.println("请输入您的登录密码：");
                    String passWord=sc.next();
                    if(acc.getPassWord().equals(passWord)){
                        System.out.println("恭喜您，"+acc.getUserName()+"先生/女士进入系统，您的卡号是："+acc.getCardId());
                        //展示登录后的操作页
                        showUserCommand(accounts,acc,sc);
                        return;
                    }else{
                        System.out.println("对不起，您输入的密码有误~~");
                    }
                }
            }else{
                System.out.println("对不起，系统中不存在该账户卡号~~");
            }
        }
    }
    /*
       展示操作页
     */
    private static void showUserCommand(ArrayList<Account> accounts,Account acc,Scanner sc){
        while (true) {
            System.out.println("==========用户操作页==========");
            System.out.println("1，查询账户");
            System.out.println("2，存款");
            System.out.println("3，取款");
            System.out.println("4，转账");
            System.out.println("5，修改密码");
            System.out.println("6，退出系统");
            System.out.println("7，注销账户");
            System.out.println("请选择");
            int command=sc.nextInt();
            switch(command){
                case 1:
                    showAccount(acc);
                    break;
                case 2:
                    depositMoney(acc,sc);
                    break;
                case 3:
                    drawMoney(acc,sc);
                    break;
                case 4:
                    transferMoney(accounts,acc,sc);
                    break;
                case 5:
                    updatePassWord(acc,sc);
                    return;
                case 6:
                    System.out.println("退出成功");
                    return;
                case 7:
                    deleteAccount(accounts,acc,sc);
                    return;
                default:
                    System.out.println("您输入的操作不正确");
            }
        }
    }
    /*
    销户功能
     */
    private static void deleteAccount(ArrayList<Account> accounts, Account acc, Scanner sc){
        System.out.println("==========用户销户操作==========");
        System.out.println("您真的要销户？y/n");
        String rs=sc.next();
        if (rs.equals("y")){
            if(acc.getMoney()>0){
                System.out.println("您的账户还有钱，不允许销户");
                return;
            }
            accounts.remove(acc);
            System.out.println("您的账户销户完成");
        } else {
            System.out.println("好的，当前账户继续保留");
        }
    }
    /*
    修改密码
     */
    private static void updatePassWord(Account acc, Scanner sc){
        System.out.println("==========用户修改密码操作==========");
        while(true){
            System.out.println("请您输入当前密码");
            String passWord=sc.next();
            if(acc.getPassWord().equals(passWord)){
                while(true){
                    System.out.println("请您输入新密码");
                    String passWordNew=sc.next();
                    System.out.println("请您再次输入新密码");
                    String passWordNew1=sc.next();
                    if(passWordNew1.equals(passWordNew)){
                        acc.setPassWord(passWordNew1);
                        System.out.println("密码修改成功");
                        return;
                    }else{
                        System.out.println("您输入的两次密码不一致");
                    }
                }
            }else{
                System.out.println("您输入的密码不正确");
            }
        }
    }
    /*
       转账功能
     */
    private static void transferMoney(ArrayList<Account> accounts,Account acc, Scanner sc){
        System.out.println("==========用户转账操作==========");
        if(accounts.size()<2){
            System.out.println("账户不足两个账户，不能转账");
            return;
        }
        if(acc.getMoney()==0){
            System.out.println("对不起，账户余额为零");
            return;
        }
        //开始转账
        while(true){
            System.out.println("请输入对方账户卡号");
            String cardId= sc.nextLine();
            if(acc.getCardId().equals(cardId)){
                System.out.println("不可自己向自己转账");
                continue;
            }
            Account account=getAccountByCardId(cardId,accounts);
            if(account==null){
                System.out.println("对方账户不存在");
            }else{
                String userName=account.getUserName();
                String tip='*'+userName.substring(1);
                System.out.println("请您输入["+tip+"]的姓氏");
                String preName=sc.nextLine();
                if(userName.startsWith(preName)){
                    while (true){
                        System.out.println("请您输入转账金额");
                        double money= sc.nextDouble();
                        if(money>acc.getMoney()){
                            System.out.println("对不起，余额不足，最多可以转"+acc.getMoney());
                        }else{
                            //余额足够
                            acc.setMoney(acc.getMoney()-money);
                            account.setMoney(account.getMoney()+money);
                            System.out.println("转账成功，账户还剩"+acc.getMoney());
                            return;
                        }
                    }
                }else{
                    System.out.println("对不起，输入的信息有误");
                }
            }
        }
    }
    /*
        取钱
     */
    private static void drawMoney(Account acc, Scanner sc){
        System.out.println("==========用户取钱操作==========");
        if(acc.getMoney()<100){
            System.out.println("对不起，当前账户中不够100元，不能取钱~~");
            return;
        }
        while (true){
            System.out.println("请您输入取款金额");
            double money=sc.nextDouble();
            if(acc.getQuotaMoney()<money){
                System.out.println("对不起，当前取款金额超过限额，不能取钱~~，最多可取"+acc.getQuotaMoney());
            }else{
                if(acc.getMoney()<money){
                    System.out.println("对不起，当前取款金额超过余额，不能取钱~~，最多可取"+acc.getMoney());
                }else{
                    acc.setMoney(acc.getMoney()-money);
                    System.out.println("取款成功");
                    showAccount(acc);
                    return;
                }
            }
        }
    }
    /*
    存钱
     */
    private static void depositMoney(Account acc,Scanner sc){
        System.out.println("==========用户存钱操作==========");
        System.out.println("请您输入存款金额");
        double money=sc.nextDouble();
        //更新账户余额
        acc.setMoney(acc.getMoney()+money);
        System.out.println("存钱成功");
        showAccount(acc);
    }
    private static void showAccount(Account acc){
        System.out.println("==========当前账户信息如下==========");
        System.out.println("|卡号"+acc.getCardId());
        System.out.println("|户主"+acc.getUserName());
        System.out.println("|余额"+acc.getMoney());
        System.out.println("|限额"+acc.getQuotaMoney());
    }
    /*
    开户的操作
     */
    private static void register(ArrayList<Account> accounts,Scanner sc){
        System.out.println("==========系统开户操作==========");
        Account account=new Account();
        System.out.println("请您输入用户名：");
        String userName=sc.next();
        account.setUserName(userName);

        while(true){
            System.out.println("请您输入账户密码：");
            String passWord=sc.next();
            System.out.println("请您确认密码：");
            String okPassWord=sc.next();
            if(okPassWord.equals(passWord)){
                account.setPassWord(passWord);
                break;
            }else{
                System.out.println("对不起，您输入的两次密码不一致，请重新确认~~");
            }
        }
        System.out.println("请您输入当次限额：");
        double quotaMoney=sc.nextDouble();
        account.setQuotaMoney(quotaMoney);
        //为账户随机一个八位且不重复的账户
        String cardId=getRandomCardId(accounts);
        account.setCardId(cardId);
        accounts.add(account);
        System.out.println("恭喜您，"+userName+"先生/女士，您开户成功，您的卡号是："+cardId+"，请您妥善保管");
    }
    private static String getRandomCardId(ArrayList<Account> accounts) {//为账户随机一个八位且不重复的账户
        Random r=new Random();
        while(true){
            StringBuilder cardId= new StringBuilder();
            for(int i=0;i<8;i++){
                cardId.append(r.nextInt(10));
            }
            //判断是否重复
            Account acc=getAccountByCardId(cardId.toString(),accounts);
            if(acc==null){
                return cardId.toString();
            }
        }
    }
    private static Account getAccountByCardId(String cardId,ArrayList<Account> accounts){//根据卡号查询账户对象出来
        for(Account acc : accounts) {
            if (acc.getCardId().equals(cardId)) {
                return acc;
            }
        }
        return null;
    }
}