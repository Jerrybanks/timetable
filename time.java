import java.util.Scanner;
 
class time
{
    public static void main(String[] args) throws Exception
    {
        int t,n,i,j,k,l,x,y;
        String a,b,c;
        Scanner s=new Scanner(System.in);
        t=s.nextInt();
        while(t>0)
        {
            n=s.nextInt();
            table tt=new table(n);
            t--;
            for(i=0;i<n;i++)
            {
                a=s.next();
                b=s.next();
                c=s.next();
                x=s.nextInt();
                y=s.nextInt();
                tt.setc(a,b,c,x,y,i);
                for(j=0;j<y;j++)
                {
                    x=s.nextInt();
                    a=s.next();
                    b=s.next();
                    tt.setcd(x,a,b,i);
                }
            }
            tt.sort();
            tt.setslot();
            tt.printc();
        }
    }
}
 
class table
{
    private course cr[];
    private int crno,tc=0;
    private slotprovider sp;
    table(int c)
    {
        this.crno=c;
        cr=new course[c];
    }
 
    public void setc(String a,String b,String c,int x,int y,int i)
    {
    	tc=tc+y;
        this.cr[i]=new course(a,b,c,x,y);
    }
 
    public void setcd(int x,String a,String b,int i)
    {
        cr[i].setcd(x,a,b);
    }
 
    public void sort()
    {
        for(int i=0;i<crno-1;i++)
        {
            for(int j=0;j<crno-i-1;j++)
            {
                if (cr[j].getprio()>cr[j+1].getprio())
                {
                    course p=cr[j];
                    cr[j]=cr[j+1];
                    cr[j+1]=p;
                }
            }
        }
    }
 
    public void printc()
    {
       sp.prd();
    }
 
    public void setslot()
    {
        this.sp=new slotprovider(tc);
    	int king=0;
    	while(tc>0)
    	{
        	for(int i=0;i<crno;i++)
        	{
        	    for(int j=king;j<cr[i].getslotno();j++)
        	    {
        	    	if (cr[i].getslotdu(j)==0)
        	    		continue;
        	        sp.set(cr[i],j);
        	        tc--;
        	        break;
        	    }
        	}
        	king++;
        }
    }
    
}
 
class slotprovider
{
    private day d[]=new day[5];
    private course sat[];
    private String a,b;
    private boolean flag;
    private int sa[]=new int[5],king;
    slotprovider(int t)
    {
    	sat=new course[t];
    	this.king =0;
        for(int i=0;i<5;i++)
        this.d[i]=new day();
    }
 
    public void prd()
    {
    	System.out.println("Monday");
    	if (d[0].getsfilled()!=0) 
    	d[0].printday();
    	System.out.println("Tuesday");
    	if (d[1].getsfilled()!=0)
    	d[1].printday();
    	System.out.println("Wednesday");
    	if (d[2].getsfilled()!=0)
    	d[2].printday();
    	System.out.println("Thursday");
    	if (d[3].getsfilled()!=0)
    	d[3].printday();
    	System.out.println("Friday");
    	if (d[4].getsfilled()!=0)
    	d[4].printday();
    	System.out.println("Saturday");
    	if (king!=0)
    	printsaturday();
    }
 
    public void sort()
    {
    	for(int i=0;i<5;i++)
    	sa[i]=i;
    	for(int i=0;i<5;i++)
        {
            for(int j=i+1;j<5;j++)
            {
                if ( d[sa[i]].getone(0)<d[sa[j]].getone(0) )
                {
                    int po=sa[j];
                    sa[j]=sa[i];
                    sa[i]=po;
                }
            }
        }
 
    }
 
    public void set(course c,int slot)
    {
        a=c.daypr(slot);
        b=c.timepr(slot);
        flag=false;
        int l1=a.length(),l2=b.length();
        int f=0,x,y=0,p[]=new int[(l2/4)];
        for(int i=0;i<l2&&(!b.equals("NIL"));i=i+4)
        {
        	p[y]=-1;
            if (b.charAt(i)=='0'&&b.charAt(i+2)=='0'&&b.charAt(i+3)=='0')
            {
                if (b.charAt(i+1)=='9')
                    p[y]=0;
                if (b.charAt(i+1)=='3')
                    p[y]=4;
                if (b.charAt(i+1)=='4')
                    p[y]=5;
                if (b.charAt(i+1)=='5')
                    p[y]=6;
            }
            if (b.charAt(i)=='1')
            {
            	if (b.charAt(i+1)=='0'&&b.charAt(i+2)=='0'&&b.charAt(i+3)=='0')
            		p[y]=1;
            	if (b.charAt(i+1)=='1'&&b.charAt(i+2)=='1'&&b.charAt(i+3)=='5')
            		p[y]=2;
            	if (b.charAt(i+1)=='2'&&b.charAt(i+2)=='1'&&b.charAt(i+3)=='5')
            		p[y]=3;
            }
            y++;
        }
        int w=c.getslotdu(slot);
        w--;
 
        sort();
        
        if ((!a.equals("NIL"))&&(b.equals("NIL")))
        {
        		for(int j=0;j<l1;j++)
                {
               		x=a.charAt(j)-49;
               		for(int i=0;i<7&&(w==0);i++)
               		{
               			if ( (d[x].help(w,i))==0 )            // if avai. fill
              			{
               				d[x].fill(w,i,c);
               				return;
               			}
               		}
                  	for(int i=0;i<4&&(w==1);i++)
               		{
               			f=0;
               			if (i==1) f=2;
               			if (i==2) f=4;
               			if (i==3) f=5;
                		if ( (d[x].help(w,f))==0 )            // if avai. fill
               			{
               				d[x].fill(w,f,c);
               				return;
               			}
                  	}
               		if ( (w==2)&&(d[x].help(2,4))==0 )            // if avai. fill
              		{
               			d[x].fill(w,4,c);
               			return;
               		}
               	}
        }
 
        if (!b.equals("NIL"))
        {
 
            if (!a.equals("NIL"))
            {
                for(int q=0;q<(l2/4)&&(p[q]!=-1);q++)
                {
                	for(int j=0;j<l1;j++)
                	{
                		x=a.charAt(j)-49;
                		if (d[x].getone(w)!=0)                      // check if day 
                		{
                       		if ( (d[x].help(w,p[q]))==0 )            // if avai. fill
               				{
                   				d[x].fill(w,p[q],c);
                   				return;
                   			}
                		}
                	}
 
 
                	for(int j=0;j<5;j++)
                	{
                		if (d[sa[j]].getone(w)!=0)
                		{
                			if ( (d[sa[j]].help(w,p[q]))==0 )            // if avai. fill
               				{
                   				d[sa[j]].fill(w,p[q],c);
                   				return;
                   			}
                		}
                	}
                }
 
				for(int j=0;j<l1;j++)
                {
               		x=a.charAt(j)-49;
               		for(int i=0;i<7&&(w==0);i++)
               		{
               			if ( (d[x].help(w,i))==0 )            // if avai. fill
              				{
               				d[x].fill(w,i,c);
               				return;
               			}
               		}
                  	for(int i=0;i<4&&(w==1);i++)
               		{
               			f=0;
               			if (i==1) f=2;
               			if (i==2) f=4;
               			if (i==3) f=5;
                		if ( (d[x].help(w,f))==0 )            // if avai. fill
               			{
               				d[x].fill(w,f,c);
               				return;
               			}
                  	}
                  	for(int i=0;i<1&&(w==2);i++)
               		{
               			if ( (d[x].help(w,4))==0 )            // if avai. fill
              			{
                  			d[x].fill(w,4,c);
                  			return;
               			}
                  	}
               	}
 
            }
            else
            {
            	for(int q=0;q<(l2/4)&&(p[q]!=-1);q++)
            	{
            		for(int j=0;j<5;j++)
                	{
                		if (d[sa[j]].getone(w)!=0)
                		{
                			if ( (d[sa[j]].help(w,p[q]))==0 )            // if avai. fill
               				{
                   				d[sa[j]].fill(w,p[q],c);
                   				return;
                   			}
                		}
                	}
                }
            }
        }
        		for(x=0;x<5;x++)
        		{
               		for(int i=0;i<7&&(w==0);i++)
               		{
               			if ( (d[sa[x]].help(w,i))==0 )            // if avai. fill
              				{
               				d[sa[x]].fill(w,i,c);
               				return;
               			}
               		}
                  		for(int i=0;i<4&&(w==1);i++)
               		{
               			f=0;
               			if (i==1) f=2;
               			if (i==2) f=4;
               			if (i==3) f=5;
                		if ( (d[sa[x]].help(w,f))==0 )            // if avai. fill
               			{
               				d[sa[x]].fill(w,f,c);
               				return;
               			}
                  	}
                  		while(w==2)
               			{
               				if ( (d[sa[x]].help(w,4))==0 )            // if avai. fill
              				{
                  				d[sa[x]].fill(w,4,c);
                  				return;
                  			}
                  			break;
                  		}
               	}               
               	sat[king]=c;
               	king++;
    }
 
    public void printsaturday()
    {
    	for(int i=king-1;i>0;i--)
    	{
    		for(int j=0;j<i;j++)
    		{
    			if ( (sat[j].getcode()).compareTo(sat[j+1].getcode())>0 )
    			{
    				course p=sat[j];
    				sat[j]=sat[j+1];
    				sat[j+1]=p;
    			}
    		}
    	}
 
    	for(int i=0;i<king;i++)
    	{
    		sat[i].printc1();
    	}
    }
}
 
class day
{
    private int a[][],one,two,three,sfilled;
    private course who1[],who2[],who3;
    day()
    {
        this.sfilled=0;
        a=new int[3][];
        a[0]=new int[7];
        a[1]=new int[4];
        a[2]=new int[1];
 
        who1=new course[7];
        who2=new course[4];
 
        for(int i=0;i<7;i++)
        {
        	a[0][i]=0;
        	if (i<4)
        	a[1][i]=0;
        	if (i==0)
        		a[2][0]=0;
        }
        this.one=7;
        this.two=4;
        this.three=1;
    }
 
    public void printday()
    {
    	if (a[0][0]==1){
    	System.out.print("9:00-10:00 "); who1[0].printc1(); }
    	if (a[0][1]==1){
    	System.out.print("10:00-11:00 "); who1[1].printc1(); }
    	if (a[1][0]==1){
    	System.out.print("9:00-11:00 "); who2[0].printc1(); }
    	
    	if (a[0][2]==1){
    	System.out.print("11:15-12:15 "); who1[2].printc1(); }
    	if (a[0][3]==1){
    	System.out.print("12:15-1:15 "); who1[3].printc1(); }
    	if (a[1][1]==1){
    	System.out.print("11:15-1:15 "); who2[1].printc1(); }
    
    	if (a[0][4]==1){
    	System.out.print("3:00-4:00 "); who1[4].printc1(); }
    	if (a[0][5]==1){
    	System.out.print("4:00-5:00 "); who1[5].printc1(); }
    	if (a[1][2]==1){
    	System.out.print("3:00-5:00 "); who2[2].printc1(); }
    	if (a[0][6]==1){
    	System.out.print("5:00-6:00 "); who1[6].printc1(); }
    	if (a[1][3]==1){
    	System.out.print("4:00-6:00 "); who2[3].printc1(); }
    	if (a[2][0]==1){
    	System.out.print("3:00-6:00 "); who3.printc1(); }
    }
 
    public  void fill(int d,int s,course c)
    {
    	if (d==0)
    		who1[s]=c;
		if (d==1)
		{
			if (s==2) 
				s=1;
			if (s==4)
				s=2;
			if (s==5)
				s=3;
			who2[s]=c;
		}
		if (d==2){
			s=0;
			who3=c;
		}
 
    	sfilled++;
    	a[d][s]=1;
    	if (d==2)
    	{
    		three--;
    		a[0][6]=a[0][4]=a[0][5]=-1;
    		a[1][2]=a[1][3]=-1;
    		one=one-3;
    		two=two-2;
    	}
    	if (d==1)
    	{
    		two--;
    		one=one-2;
    		if (s==0) { a[0][0]=a[0][1]=-1; }
    		if (s==1) { a[0][2]=a[0][3]=-1; }
    		if (s==2) { a[0][4]=a[0][5]=-1; a[1][3]=-1; two--; a[2][0]=-1; three--; }
    		if (s==3) { a[0][5]=a[0][6]=-1; a[1][2]=-1; two--; a[2][0]=-1; three--; }
    	}
    	if (d==0)
    	{
    		one--;
    		two=two--;
    		if (s==0||s==1) {a[1][0]=-1; }
    		if (s==2||s==3) {a[1][1]=-1; }
    		if (s==4) {a[1][2]=-1; a[2][0]=-1; three--; }
    		if (s==5) {a[1][2]=a[1][3]=-1; a[2][0]=-1; three--; two--; }
    		if (s==6) {a[1][3]=-1; a[2][0]=-1; three--; }
    	}
    }
 
    public int help(int d,int s)
    {
    	if  (!(d==0||d==1||d==2) )
    		return -1;
    	if (!(s==0||s==1||s==2||s==3||s==4||s==5||s==6))
    		return -1;
    	if (d==1)
    	{
    		if (s==1||s==3||s==6)
    			return -1;
    	}
    	if (d==2&&(s!=4))
    		return -1;

    	if (d==1)
		{
			if (s==2) 
				s=1;
			if (s==4)
				s=2;
			if (s==5)
				s=3;
		}
		if (d==2)
			s=0;
    	return a[d][s];
    }
 
    public int getsfilled()
    {
        return sfilled;
    }
 
    public int getone(int w)
    {
    	if (w==0)
        return one;
    	else if (w==1)
    	return two;
    	else
    	return three;
    }
}
 
class course
{
    private String code,name,prof;
    private int prio,slots,slotdu[],i=0;
    private String slotde[][];
 
    course(String a,String b,String c,int x,int y)
    {
        this.code=a;
        this.name=b;
        this.prof=c;
        this.prio=x;
        this.slots=y;
        slotdu=new int[y];
        slotde=new String[y][3];
    }
 
    public void setcd(int x,String a,String b)
    {
        this.slotdu[i]=x;
        this.slotde[i][0]=a;
        this.slotde[i][1]=b;
        i++;
    }
 
    public String getcode()
    {
    	return code;
    }
 
    public int getprio()
    {
        return prio;
    }
 
    public int getslotno()
    {
        return slots;
    }
 
    public int getslotdu(int j)
    {
        return slotdu[j];
    }
 
    public String daypr(int j)
    {
        return slotde[j][0];
    }
 
    public String timepr(int j)
    {
        return slotde[j][1];
    }
 
    public void printc1()
    {
        System.out.println(code+" "+name+" "+prof);
    }
 
    public void printc2()
    {
        for(int k=0;k<slots;k++)
        System.out.print(slotdu[k]+" "+slotde[k][0]+" "+slotde[k][1]);
    }
}